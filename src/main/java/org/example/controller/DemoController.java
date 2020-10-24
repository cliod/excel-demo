package org.example.controller;

import org.example.entity.ExportedEntity;
import org.example.excel.ExcelUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * API - TEST
 *
 * @author cliod
 * @since 10/24/20 10:48 AM
 */
@RestController
public class DemoController {

	@GetMapping("/demo")
	public void demo(HttpServletResponse response) {
		Random random = new Random();
		List<ExportedEntity> entities = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			ExportedEntity entity = new ExportedEntity();
			entity.setNickname("测试名称" + i);
			entity.setOrderNo(UUID.randomUUID().toString().replaceAll("-", ""));
			List<ExportedEntity.Goods> goods = new ArrayList<>();
			for (int j = 0; j < random.nextInt(3) + 1; j++) {
				ExportedEntity.Goods good = new ExportedEntity.Goods();
				good.setCategoryL1("测试分类1-" + i + j);
				good.setCategoryL2("测试分类2-" + i + j);
				good.setGoodsNo(UUID.randomUUID().toString().replaceAll("-", ""));
				good.setGoodsName("测试" + i + j);
				good.setGoodsUnit("斤");
				good.setGoodsQuantity(BigDecimal.valueOf(random.nextInt(300) + 100 * 1.00 / 100.00));
				good.setGoodsUnivalent(BigDecimal.valueOf((random.nextInt(300) + 100) * 1.00 / 100.00));
				good.setShippedUnivalent(BigDecimal.valueOf((random.nextInt(300) + 100) * 1.00 / 100.00));
				good.setSortingQuantity(BigDecimal.valueOf((random.nextInt(300) + 100) * 1.00 / 100.00));
				good.setSortingSubtotal(BigDecimal.valueOf((random.nextInt(300) + 100) * 1.00 / 100.00));
				goods.add(good);
			}
			entity.setGoods(goods);
			entities.add(entity);
		}
		try {
			ExcelUtils.export(response, entities);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
