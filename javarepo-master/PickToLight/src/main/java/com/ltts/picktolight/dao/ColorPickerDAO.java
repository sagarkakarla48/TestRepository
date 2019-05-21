/**
 * 
 */
package com.ltts.picktolight.dao;

import java.util.List;

import com.ltts.picktolight.domain.ColorPicker;

/**
 * @author 90001332
 *
 */
public interface ColorPickerDAO {
	
	public List<ColorPicker> getColorList();
	
	public String updateColorStatus(ColorPicker cpobj);

}
