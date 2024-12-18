package com.ikun.cm.pojo;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * @author: HeKun
 * @date: 2024/10/21 15:02
 * @description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticData {

    @Id
    String _id;

    String name;

    List<String> strList;

    List<JSONObject> jsonList;

}
