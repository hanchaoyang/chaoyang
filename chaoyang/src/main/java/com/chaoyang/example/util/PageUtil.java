package com.chaoyang.example.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页工具
 *
 * @author 韩朝阳
 * @since 2023/8/24
 */
public class PageUtil {

    /**
     * MP分页对象转换，例如Page<PO>转Page<DTO>
     */
    public static <T, R> Page<R> convert(Page<T> source, List<R> records) {
        Page<R> target = new Page<>();

        BeanUtils.copyProperties(source, target, "records");

        target.setRecords(records);

        return target;
    }

    /**
     * MP分页对象转换，例如Page<PO>转Page<DTO>
     */
    public static <T, R> Page<R> convert(Page<T> source, Function<T, R> mapper) {
        Page<R> target = new Page<>();

        BeanUtils.copyProperties(source, target, "records");

        target.setRecords(source.getRecords().stream().map(mapper).collect(Collectors.toList()));

        return target;
    }

}