package com.tty.twsearch.mapper;

import com.tty.twsearch.pojo.TwitterData;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author :   Tianyi Tang
 * @date :   Created in 2019-11-30 18:41
 */
@Mapper
@Repository
public interface TwitterMapper {

    @Select("select * from t_twitter")
    List<TwitterData> getAll();

    @Select("select * from t_twitter_temp")
    List<TwitterData> getAllTemp();

    @Insert({"<script>",
                "insert into t_twitter_temp values",
                "<foreach item='item' index='index' collection='list' separator=','>",
                    "(null, #{item.twDate}, #{item.twString})",
                "</foreach>",
            "</script>"})
    int saveListTemp(@Param("list") List<TwitterData> list);

    @Insert({"<script>",
            "insert into t_twitter values",
            "<foreach item='item' index='index' collection='list' separator=','>",
            "(null, #{item.twDate}, #{item.twString})",
            "</foreach>",
            "</script>"})
    int saveList(@Param("list") List<TwitterData> list);

    @Update("truncate table t_twitter_temp")
    int deleteAll();
}
