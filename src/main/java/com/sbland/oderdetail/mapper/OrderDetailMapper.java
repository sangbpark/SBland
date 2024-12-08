package com.sbland.oderdetail.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.sbland.oderdetail.domain.OrderDetail;

@Mapper
public interface OrderDetailMapper {
	public List<OrderDetail> selectOrderDetailByOrderId(Long orderId);
	
	public int insertOrderDetail(List<OrderDetail> orderDetailList);
}
