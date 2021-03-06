package com.muyun.admin.vo;

import com.muyun.admin.entity.Log;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Table;

/**
 * @author muyun
 * @date 2020/6/27
 */
@Data
@Table(appliesTo = "log")
@NoArgsConstructor
public class LogVO extends Log {

    private String username;

    private String name;
}
