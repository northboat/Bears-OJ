package com.oj.neuqoj.api;

import com.oj.neuqoj.mapper.PaintingMapper;
import com.oj.neuqoj.pojo.Painting;
import com.oj.neuqoj.utils.ResultCode;
import com.oj.neuqoj.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class PaintingApi {

    private PaintingMapper paintingMapper;
    @Autowired
    public void setPaintingMapper(PaintingMapper paintingMapper){
        this.paintingMapper = paintingMapper;
    }


    @RequestMapping("/getPaintings")
    public ResultUtil getPaintings(){
        return ResultUtil.success(paintingMapper.getPaintings());
    }

    @RequestMapping("/submitPainting")
    public ResultUtil submitPainting(@RequestBody Map<String, Object> params){
        try{
            String title = params.get("title").toString();
            String desc = params.get("desc").toString();
            String from = params.get("from").toString();
            String content = params.get("content").toString();
            Painting painting = new Painting(title, desc, from, content);

            paintingMapper.addPainting(painting);
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.failure(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultUtil.success();
    }

    @RequestMapping("/getPaintingsByName")
    public ResultUtil getPaintingsByName(@RequestBody Map<String, String> params){
        String name = params.get("name");
        List<Painting> paintings = paintingMapper.getPaintingsByName(name);
        return ResultUtil.success(paintings);
    }

    @RequestMapping("/deletePainting")
    public ResultUtil deletePainting(@RequestBody Map<String, String> params){
        int num = Integer.parseInt(params.get("num"));
        paintingMapper.deletePainting(num);
        return ResultUtil.success();
    }
}
