package bxt.unilectures.vorkurs.visitenkarte;

import org.apache.commons.lang.StringUtils;

public class BoxBuilder {

	int width;
	StringBuilder outStr=null;
	
	protected static class Border {
			public static final char vertical='|';
			public static final char horizontal='-';
			public static final char edge='*';
			public static final int verticalPadding=3;
			public static final char space=' ';
		}

	protected void renderLineLeftAligned(String line) {
		renderLine(StringUtils.rightPad(line, width));
	}

	protected void renderLineRightAligned(String line) {
		renderLine(StringUtils.leftPad(line, width));
	}

	protected void renderLineBorder() {
		outStr.append(Border.edge);
		for(int i=0;i<width+Border.verticalPadding*2;i++) {
			outStr.append(Border.horizontal);
		}
		outStr.append(Border.edge);
		outStr.append("\n");
	}

	private void renderLine(String line) {
		outStr.append(Border.vertical);
		for(int i=0;i<Border.verticalPadding;i++) {
			outStr.append(Border.space);
		}
		outStr.append(StringUtils.abbreviate(line, width));
		for(int i=0;i<Border.verticalPadding;i++) {
			outStr.append(Border.space);
		}
		outStr.append(Border.vertical);
		outStr.append("\n");
	}

}