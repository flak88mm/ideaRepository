package com.brt.device.rest.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;
import java.util.List;

@ApiModel(value = "业务设备vo")
public class ViewBusinessDevice {
    @ApiModelProperty(value = "业务设备id")
    private Long id;
    @ApiModelProperty(value = "业务设备编号")
    private String code;
    @ApiModelProperty(value = "业务设备名称")
    private String name;
    @ApiModelProperty(value = "预留字段")
    private String reserve;
    @ApiModelProperty(value = "业务设备详情")
    private String detail;
    @ApiModelProperty(value = "业务设备类型")
    private String deviceType;
    @ApiModelProperty(value = "业务设备创建时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "业务设备更新时间")
    private Timestamp updateTime;
    @ApiModelProperty(value = "关联定位设备")
    private ViewLocationDevice viewlocationDevice;
    @ApiModelProperty(value = "关联报警设备")
    private ViewReportDevice viewReportDevice;
    @ApiModelProperty(value = "关联摄像头设备组")
    private List<ViewCameraDevice> viewCameraDevices;

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

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
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

    public ViewLocationDevice getViewLocationDevice() {
        return viewlocationDevice;
    }

    public void setViewLocationDevice(ViewLocationDevice viewlocationDevice) {
        this.viewlocationDevice = viewlocationDevice;
    }

    public ViewReportDevice getViewReportDevice() {
        return viewReportDevice;
    }

    public void setViewReportDevice(ViewReportDevice viewReportDevice) {
        this.viewReportDevice = viewReportDevice;
    }

    public List<ViewCameraDevice> getViewCameraDevices() {
        return viewCameraDevices;
    }

    public void setViewCameraDevices(List<ViewCameraDevice> viewCameraDevices) {
        this.viewCameraDevices = viewCameraDevices;
    }

    @Override
    public String toString() {
        return "ViewBusinessDevice{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", reserve='" + reserve + '\'' +
                ", detail='" + detail + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", viewLocationDevice=" + viewlocationDevice +
                ", viewReportDevice=" + viewReportDevice +
                ", viewCameraDevices=" + viewCameraDevices +
                '}';
    }
}
