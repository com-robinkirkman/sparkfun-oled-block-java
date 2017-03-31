package org.esialb.edison.sfo;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TextImagesTest {
	@Test
	public void testSplit() {
		List<String> split = TextImages.wrappedSplit(1000, "Hello World\nHowRU");
		Assert.assertEquals(2, split.size());
	}
}
