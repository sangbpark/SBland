package com.sbland.order.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbland.order.domain.Order;

@Mapper
public interface OrderMapper {
	
	public int insertOrder(Order order);
	
	public List<Order> selectOrderByUserId(
			@Param("userId") Long userId,
			@Param("count") int count,
			@Param("offset") int offset);
	
	public List<Order> selectOrderByStatus(
			@Param("status") String status,
			@Param("count") int count,
			@Param("offset") int offset);
	
	public Order selectOrderById(Long id);
	
	public Order selectOrderByIdAndUserId(
			@Param("id") Long id, 
			@Param("userId") Long userId);
	
	public int updateOrderStatus(
			@Param("id") Long id, 
			@Param("userId") Long userId,
			@Param("status") String status);
	
	public int deleteOrderByOrderId(Long id);
	
	public int selectOrderListSizeByUserId(Long userId);
	
	public int selectOrderListSizeByStatus(String status);
	
}
