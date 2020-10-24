package org.example.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.example.entity.ExportedEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 导出模型
 *
 * @author cliod
 * @since 10/24/20 9:31 AM
 */
@Data
public class ExportedModel {
	@ExcelProperty(value = "会员昵称")
	private String nickname;
	@ExcelProperty(value = "订单编号")
	private String orderNo;
	@ExcelProperty(value = "一级分类")
	private String categoryL1;
	@ExcelProperty(value = "二级分类")
	private String categoryL2;
	@ExcelProperty(value = "商品编号")
	private String goodsNo;
	@ExcelProperty(value = "商品名称")
	private String goodsName;
	@ExcelProperty(value = "下单单位")
	private String goodsUnit;
	@ExcelProperty(value = "下单数量")
	private String goodsQuantity;
	@ExcelProperty(value = "下单单价")
	private String goodsUnivalent;
	@ExcelProperty(value = "发货单价")
	private String shippedUnivalent;
	@ExcelProperty(value = "分捡数量")
	private String sortingQuantity;
	@ExcelProperty(value = "分捡小计")
	private String sortingSubtotal;

	public static List<ExportedModel> from(ExportedEntity entity) {
		List<ExportedModel> list = new ArrayList<>();
		ExportedModel model;
		BigDecimal sum = BigDecimal.ZERO;
		for (ExportedEntity.Goods good : entity.getGoods()) {
			model = new ExportedModel();
			model.setNickname(entity.getNickname());
			model.setOrderNo(entity.getOrderNo());

			model.setCategoryL1(good.getCategoryL1());
			model.setCategoryL2(good.getCategoryL2());
			model.setGoodsNo(good.getGoodsNo());
			model.setGoodsName(good.getGoodsName());
			model.setGoodsUnit(good.getGoodsUnit());
			model.setGoodsQuantity(toString(good.getGoodsQuantity()));
			model.setGoodsUnivalent(toString(good.getGoodsUnivalent()));
			model.setShippedUnivalent(toString(good.getShippedUnivalent()));
			model.setSortingQuantity(toString(good.getSortingQuantity()));
			model.setSortingSubtotal(toString(good.getSortingSubtotal()));

			sum = add(sum, good.getSortingSubtotal());

			list.add(model);
		}
		model = ExportedModel.newNull();
		model.setSortingQuantity("合计：");
		model.setSortingSubtotal(toString(sum));
		list.add(model);
		model = ExportedModel.newNull();
		list.add(model);
		return list;
	}

	public static String toString(BigDecimal number) {
		if (Objects.isNull(number)) {return "0";}
		return number.toString();
	}

	public static BigDecimal add(BigDecimal... numbers) {
		if (Objects.isNull(numbers)) {
			return BigDecimal.ZERO;
		}
		BigDecimal sum = BigDecimal.ZERO;
		for (BigDecimal number : numbers) {
			if (Objects.nonNull(number)) {
				sum = sum.add(number);
			}
		}
		return sum;
	}

	public static ExportedModel newNull() {
		ExportedModel model = new ExportedModel();
		model.setNickname("");
		model.setOrderNo("");
		model.setCategoryL1("");
		model.setCategoryL2("");
		model.setGoodsNo("");
		model.setGoodsName("");
		model.setGoodsUnit("");
		model.setGoodsQuantity("");
		model.setGoodsUnivalent("");
		model.setShippedUnivalent("");
		model.setSortingQuantity("");
		model.setSortingSubtotal("");
		return model;
	}
}
