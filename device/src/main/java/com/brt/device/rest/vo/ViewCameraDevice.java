package com.brt.device.rest.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

@ApiModel(value = "摄像头设备vo")
public class ViewCameraDevice {
    @ApiModelProperty(value = "摄像头设备id")
    private Long id;
    @ApiModelProperty(value = "摄像头设备编号")
    private String code;
    @ApiModelProperty(value = "摄像头设备名称")
    private String name;
    @ApiModelProperty(value = "预留字段")
    private String reserve;
    @ApiModelProperty(value = "摄像头设备详情")
    private String detail;
    @ApiModelProperty(value = "摄像头设备创建时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "摄像头设备更新时间")
    private Timestamp updateTime;
    @ApiModelProperty(value = "关联业务设备id")
    private Long businessDeviceId;
    @ApiModelProperty(value = "关联业务设备名称")
    private String businessDeviceName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Long getBusinessDeviceId() {
        return businessDeviceId;
    }

    public void setBusinessDeviceId(Long businessDeviceId) {
        this.businessDeviceId = businessDeviceId;
    }

    public String getBusinessDeviceName() {
        return businessDeviceName;
    }

    public void setBusinessDeviceName(String businessDeviceName) {
        this.businessDeviceName = businessDeviceName;
    }

    @Override
    public String toString() {
        return "ViewCameraDevice{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", reserve='" + reserve + '\'' +
                ", detail='" + detail + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", businessDeviceId=" + businessDeviceId +
                ", businessDeviceName='" + businessDeviceName + '\'' +
                '}';
    }
}
