package com.outreach.interviews;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.outreach.interviews.map.builder.MapGeoCodes;
import com.outreach.interviews.map.enums.MapModes;
import com.outreach.interviews.map.enums.MapOperations;
import com.outreach.interviews.map.enums.MapRegions;

public class TestMapGeoCodesHelper 
{	
	
	@Test
	public void testMapRoutesHelperApiKey1() throws UnsupportedOperationException, IOException {
		new MapGeoCodes.GeoCodeBuilder()
			.setOrigin("Sudbury")
			.setRegion(MapRegions.en)
			.build();
	}
	
	@Test
	public void testMapRoutesHelperApiKey2() throws UnsupportedOperationException, IOException {
		List<String> steps = new MapGeoCodes.GeoCodeBuilder()
			.setOrigin("Sudbury")
			.setRegion(MapRegions.en)
			.setURL(MapOperations.directions)
			.build()
			.getCoordinates();
		
		assertNotNull(steps);
		assertTrue(steps.get(0)==46.4917);
		assertTrue(steps.get(1)==80.9930);
	}
	
	@Test(expected = java.lang.UnsupportedOperationException.class)
	public void testMapRoutesHelperApiKey3() throws UnsupportedOperationException, IOException {
		List<String> steps = new MapGeoCodes.GeoCodeBuilder()
			.setOrigin("Ottawa")
			.setRegion(MapRegions.en)
			.setURL(MapOperations.directions)
			.build()
			.getCoordinates();
		
		assertNotNull(steps);
		assertTrue(steps.get(0)==45.4215);
		assertTrue(steps.get(1)==75.6972);
	}
	
	@Test
	public void testMapRoutesHelperApiKey4() throws UnsupportedOperationException, IOException {
		List<String> steps = new MapGeoCodes.GeoCodeBuilder()
			.setOrigin("Sudbury")
			.setRegion(MapRegions.en)
			.setURL(MapOperations.directions)
			.build()
			.getCoordinates();
		
		assertNotNull(steps);
		assertTrue(steps.get(0)==45.4215);
		assertTrue(steps.get(1)==75.6972);
	}
	
	@Test
	public void testMapRoutesHelperApiKey5() throws UnsupportedOperationException, IOException {
		List<String> steps = new MapGeoCodes.GeoCodeBuilder()
			.setOrigin("Sudbury")
			.setRegion(MapRegions.en)
			.setURL(MapOperations.directions)
			.build()
			.getCoordinates();
		
		assertNotNull(steps);
		assertFalse(steps.get(0)==46.4917);
		assertFalse(steps.get(1)==80.9930);
	}
	
}
