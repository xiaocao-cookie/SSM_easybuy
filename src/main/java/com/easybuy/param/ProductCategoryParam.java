package com.easybuy.param;

import com.easybuy.entity.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryParam extends ProductCategory{
	private Integer startIndex;
	private Integer pageSize;
	private boolean isPage=false;
	private String sort;
}
