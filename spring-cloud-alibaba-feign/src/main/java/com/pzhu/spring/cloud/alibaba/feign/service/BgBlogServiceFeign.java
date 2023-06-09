package com.pzhu.spring.cloud.alibaba.feign.service;

import com.pzhu.spring.cloud.alibaba.common.domain.BgBlog;
import com.pzhu.spring.cloud.alibaba.feign.sentinel.BgBlogServiceFeignSentinel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * feign接口
 * @Author Jin haiyang
 * @Date 2020/3/16 16:41
 */
@FeignClient(value = "nacos-provider",fallback = BgBlogServiceFeignSentinel.class)
public interface BgBlogServiceFeign {

    /**
     * 查询接口
     * @return
     */
    @GetMapping("list")
    List<BgBlog> list();

}
