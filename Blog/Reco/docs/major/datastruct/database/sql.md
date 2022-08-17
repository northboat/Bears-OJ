---
title: SQL
date: 2022-4-21
tags:
  - DataBase
---

## 库操作

### 建库

Mysql.8.0.28没有限定储存文件大小的命令，查阅资料得知

- MySQL.5.6以后允许InnoDB表最多1017个列
- 一个InnoDB表最多允许64个二级索引
- 默认索引前缀长度最多767bytes
- 联合索引最多允许16个列, 多了报错
- InnoDB的最大行大小为半页(小于8K-默认)，由于默认页大小为16K, 要求是小于half page size, 就是小于8K;

~~~sql
create database test1;
create database test2;
~~~

### 更名

由于在`mysql-5.1.23`后出于安全考虑丢弃了rename database、modify name这些命令，采用先备份原数据库数据，新建数据库并导入数据的方式进行更名

~~~sql
mysqldump -u root -p --set-gtid-purged=OFF test1 > C:\Files\sql\old.sql
drop database test1;
create database new_test1;
mysql -u root -p new_test1 < C:\Files\sql\old.sql
~~~

### 删除库

~~~sql
drop database new_test1;
drop database test2;  
~~~

## 表操作

### 建表

建立学生信息表`student`、课程表`course`、学生课程成绩关系表`score`，其中`score`的外键为学生ID和课程ID

~~~sql
create table `student`(
    student_id varchar(10) not null comment '学号',
    student_name varchar(10) not null comment '姓名',
    sex char(1) not null comment '性别F或M',
    age int comment '年龄',
    department varchar(15) default 'computer' comment '系名',
    primary key(`student_id`)
)engine=innodb default charset=utf8;


create table `course`(
    course_id varchar(6) not null comment '课程号',
    course_name varchar(20) not null comment '课程名',
    pre_could varchar(6) not null comment '先修课程号',
    crdits float(3,1) comment '学分',
    primary key(`course_id`)
)engine=innodb default charset=utf8;
    
create table `score`(
    student_id varchar(10) not null comment '学号号',
    course_id varchar(6) not null comment '课程号',
    grade float(3,1) comment '学分',
    check(grade>0 and grade < 100),
    primary key(`student_id`,`course_id`)
)engine=innodb default charset=utf8;
~~~

关联外键

~~~sql
alter table score
add constraint fk_student_id
foreign key(student_id)
references student(student_id);

alter table score
add constraint fk_course_id
foreign key(course_id)
references course(course_id);
~~~

### 修改列属性

添加memo字段

~~~sql
alter table `student` add memo varchar(200) after `age`;
~~~

- 使用`after`可以限定新增字段的位置，默认在最后一列

改变memo属性

~~~sql
alter table `student` modify memo varchar(300);
~~~

删除memo字段

~~~sql
alter table `student` drop memo;
~~~

### 删除表

~~~sql
drop table student, score, course;
~~~

## CUD操作

> create、update、delete

### 批量插入

在上述`student、course、score`的基础上批量插入数据

~~~sql
insert into student (student_id,student_name,sex,age,department) values
('20010101', 'Jone',  '女', '19','Computer'),
('20010102', 'Sue',   '男', '20','Computer'),
('20010103', 'Smith', '女', '19','Math'),
('20030101', 'Allen', '女', '18','Automation'),
('20030102','deepa', '男', '21','Art'),
('20010104','Stefen','男', '20','Computer');

insert into studentInfo.dbo.course(course_id,course_name,precould,credits) values
('C1','English',' ','4'),
('C2','Math','C1','2'),
('C3','Cprogram','C2','2'),
('C4','database','C2''2');

insert into studentInfo.dbo.score(student_id,course_id,grade) values
('20010101','C1','90'),
('20010102','C1','87'),
('20010103','C1','88'),
('20010102','C2','90'),
('20010104','C2','94'),
('20010102','C3','62'),
('20030101','C3','80'),
('20010103','C4','77');
~~~

### 单个插入

向students表添加一个学生记录，学号为20010112，性别为男，姓名为`stefen`，年龄25岁，所在系为艺术系art

~~~sql
insert into student (student_id, sex, student_name, age, department) values
('2010112', 'M', 'stefen', 25, 'art');
~~~

向score表添加一个选课记录，学生学号为20010112，所选课程号为C2

~~~sql
insert into score (student_id, course_id) values('20010112', 'C2');
~~~

### 复制表

建立表`tempstudent`，结构与`students`结构相同，其记录均从`student`表获取

~~~sql
create table tempstudent as(select * from student);
~~~

### 修改数据

将所有学生的成绩加5分

~~~sql
update score set grade=grade+5;
~~~

将姓名为sue的学生所在系改为电子信息系

~~~sql
update student set department='电子信息' where student_name='Sue';
~~~

将选课为database的学生成绩加10分

~~~sql
update score set grade=grade+10
where course_id = (select course_id from course where course_name='database');
~~~

### 删除数据

删除所有成绩为空的选修记录（delete form 表名 where 属性列 is null）

~~~sql
delete from score where grade is null;
~~~

删除学生姓名为`deepa`的学生记录

~~~sql
delete from score where student_id = (select student_id from student where student_name='deepa');
delete from student where student_name = 'deepa' limit 1;
~~~

- 先删关联表，再删数据表
- 定向删除单个数据时使用`limit 1`能够防止查询整个表

删除计算机系选修成绩不及格的学生的选修记录

~~~sql
delete from score where grade < 60 and 
student_id in (select student_id from student where department='computer');
~~~

## R操作

> retrieve查询

### 单表查询

#### 设置别名

查询全体学生的学号、姓名、所在系，并为结果集的各列设置中文名称

~~~sql
select student_id as '学号', student_name as '学生姓名', 
sex as '性别', age as '年龄', department as '所在系' 
from student;
~~~

- `id as '学号'`

#### 查询同时修改

不改变原数据

查询全体学生的选课情况，并为所有成绩加5分

 ~~~sql
 select student_id as 学号,course_id as 课程,grade+'5' as 成绩 FROM score;
 ~~~

#### 去重

显示所有选课学生的学号，去掉重复行

 ~~~sql
 select distinct student_id from score;
 ~~~

- distinct

#### 条件判断

**数字判断**

查询选课成绩大于80分的学生

 ~~~sql
 select distinct student_id from score where grade>80;
 ~~~

查询年龄在20到30之间的学生学号，姓名，所在系

 ~~~sql
 select student_id, student_name, department from student where age>20 and age<30;
 ~~~

- 用`and/or`连接条件判断

**字符串判断**

查询数学系、计算机系、艺术系的学生学号，姓名

 ~~~sql
 select student_id, student_name from student where 
 department='math'
 or department='computer'
 or department='art';
 ~~~

查询姓名第二个字符为u并且只有3个字符的学生学号，姓名

 ~~~sql
 select student_id,student_name from student where student_name like('_u_');
 ~~~

- `_`表示单个任一字符

查询所有以S开头的学生

 ~~~sql
 select * from student where student_name like 'S%';
 ~~~

- `%`表示`whatever`，不限长度
- 模糊搜索`like`

查询姓名不以S、D、或J开头的学生

 ~~~sql
 select * from student 
 where student_name not like 'S%' 
 and student_name not like 'D%' 
 and student_name not like 'J%';
 ~~~

- `not like`

查询没有考试成绩的学生和相应课程号（成绩值为空）is null

 ~~~sql
 select student_id, course_id from score where grade is null;
 ~~~

- 为空`is null`

#### 函数

**计数**

求年龄大于19岁的学生的总人数

 ~~~sql
 select count(*) from student where age>19;
 ~~~

- 求满足要求的行的数量

**求均值**

求选修了c语言课程的学生平均成绩、最高分、最低分学生

 ~~~sql
 select avg(grade) from course, score
 where course.course_id=score.course_id and course_name='Cprogram';
 ~~~

**排序**

求选修了c语言课程的最高分、最低分学生

~~~sql
select student_name from student, score, course
where student.student_id=score.student_id
and score.course_id=course.course_id
and course.course_name='Cprogram'
order by score.grade DESC limit 1;

select student_name from student, score, course
where student.student_id=score.student_id
and score.course_id=course.course_id
and course.course_name='Cprogram'
order by score.grade limit 1;
~~~

- 默认排序为升序，即`incr`，若要降序加`desc`即可

**求和**

求学号为20010102的学生总成绩

~~~sql
select sum(grade) from score where student_id='20010102';
~~~

**分组**

求每个选课学生的学号，姓名，总成绩

~~~sql
select student.student_id, student_name, sum(grade) from student, score where score.student_id=student.student_id group by student.student_id;
~~~

- `group by`

求课程号及相应课程的所有的选课人数

 ~~~sql
 select course_id, count(course_id) from score group by course_id;
 ~~~

**附加条件**

查询选修了3门以上课程的学生姓名学号

~~~sql
select student_id, student_name from student
where student_id in
(select student_id from score group by student_id having count(course_id)>3);
~~~

- `having`，有点像`where`

### 多表查询

查询每个学生基本信息及选课情况

 ~~~sql
 select student.*, course_id from student, score where student.student_id = score.student_id;
 ~~~

- 相同的列需要用`表名.列名`的形式加以区分

查询每个学生学号姓名及选修的课程名、成绩

 ~~~sql
 select student_name, course_id, grade from student, score where student.student_id=score.student_id;
 ~~~

求计算机系选修课程超过2门课的学生学号姓名、平均成绩并按平均成绩降序排列

 ~~~sql
 select student.student_id, student_name, avg(grade) from student, score
 where student.student_id = score.student_id
 and department='computer'
 group by student.student_id having count(grade)>2
 order by avg(grade) desc;
 ~~~

- `order by ... desc` 从高到低排列

查询与sue在同一个系学习的所有学生的学号姓名

~~~sql
select student_id, student_name from student
where department = (select department from student where student_name = 'Sue');
~~~

查询所有学生的选课情况，要求包括所有选修了课程的和没选课的学生，显示他们的姓名学号课程号和成绩（若有）

 ~~~sql
 select student.student_id, student_name, score.course_id, grade from student
 left outer join score on (student.student_id = score.student_id)
 left outer join course on (score.course_id = course.course_id);
 ~~~

- `left outer join`左外连接

 ## 视图和存储过程

### 视图

> 类似于一张表，但不以表的形式占用储存空间

建立数学系的学生视图

 ~~~sql
 create view math as select * from student where department='math';
 ~~~

建立计算机系选修了课程名为`database`的学生的视图，视图名为`cs_db`，视图列名为学号、姓名、成绩

 ~~~sql
 create view cs_db
 as select student.student_id as '学号', student_name as '姓名', grade as '成绩' from student, score
 where student.student_id = score.student_id
 and department = 'computer'
 and course_id in (select course_id from course where course_name = 'database');
 ~~~

创建一个名为`stu_sum`的视图，包含所有学生学号和总成绩

 ~~~sql
 create view stu_sum as select student_id as '学号', sum(grade) as '总成绩'
 from score group by student_id;
 ~~~

建立一个计算机系学生选修了课程名为`database`并且成绩大于80分的学生视图，视图名为`cs_db1`，视图的列为学号姓名成绩

 ~~~sql
 create view cs_db1 as
 select student.student_id as '学号', student_name as '姓名', grade as '成绩' from student, score
 where student.student_id = score.student_id
 and department = 'computer'
 and grade > 80
 and course_id in (select course_id from course where course_name = 'database');
 ~~~

删除`cs_db1`视图

 ~~~sql
 drop view cs_db1;
 ~~~

### 存储过程

创建对studentinfo数据库表student进行插入、修改和删除的三个存储过程：`insertStu、updateStu、deleteStu`

 ~~~sql
 create procedure insertStu as
 insert into student values('202012143', 'wdnmd', 'M', '21', 'nmsl');
 ~~~

~~~sql
create procedure updateStu as
update student set department = 'cs' where student_id = '202012143';
~~~

~~~sql
create procedure deleteStu as
delete student where student_id = 202012143';
~~~





 



 

 



 

 

