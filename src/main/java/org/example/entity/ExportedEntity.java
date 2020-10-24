package org.example.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 导出实体对象
 *
 * @author cliod
 * @since 10/24/20 10:15 AM
 */
@Data
public class ExportedEntity {

	private String nickname;
	private String orderNo;

	private List<Goods> goods;

	@Data
	public static class Goods {
		private String categoryL1;
		private String categoryL2;
		private String goodsNo;
		private String goodsName;
		private String goodsUnit;
		private BigDecimal goodsQuantity;
		private BigDecimal goodsUnivalent;
		private BigDecimal shippedUnivalent;
		private BigDecimal sortingQuantity;
		private BigDecimal sortingSubtotal;
	}
}
