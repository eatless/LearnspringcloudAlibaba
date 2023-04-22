package com.pzhu.spring.cloud.alibaba.consumer.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;


@Data
@Accessors(chain = true)
public class UploadFileReqDTO {
    @ApiModelProperty(value = "简历id")
    private String resumeId;
    @ApiModelProperty(value = "渠道侧职位id")
    private String sourceJobId;
    @ApiModelProperty(value = "上传的附件")
    private MultipartFile file;
    @ApiModelProperty(value = "moka职位id")
    private String mokaJobId;
    @ApiModelProperty(value = "文件类型。0：附件；1：原始简历")
    private int type;
    @ApiModelProperty(value = "第三方渠道名称")
    private String channel;
    @ApiModelProperty(value = "orgId")
    private String companyId;
}
