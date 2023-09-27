package GraphPrim;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Arrays;

import javax.swing.JPanel;

public class DrawGraph extends JPanel {
	int x;
	int y;
	int p;
	DrawGraph point1;
	DrawGraph point2;
	String weight;
	boolean isAdd = false;
	boolean setcolor = false;

	public DrawGraph(int x, int y, int weight) {
		this.x = x;
		this.y = y;
		this.p = weight;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the p
	 */
	public int getP() {
		return p;
	}

	/**
	 * @param weight the p to set
	 */
	public void setP(int weight) {
		this.p = weight;
	}

	public void setDoublePoint(DrawGraph point1, DrawGraph point2) {
		this.point1 = point1;
		this.point2 = point2;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String newWeight) {
		this.weight = newWeight;
	}

	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawOval(x - 10, y - 10, 20, 20);
		String s = p + "";
		g.drawString(s, x, y);
	}
	public void updatePanel() {
		removeAll();
	}
	

	public void drawVertex(Graphics g) {
		if(setcolor)g.setColor(Color.RED);
		super.paintComponent(g);

		// Set color for shape
		g.setColor(Color.BLACK);

		// Draw a filled circle for vertex
		int radius = 15;
		int xCoord = x;
		int yCoord = y + 100;
		g.fillOval(xCoord, yCoord, radius * 2, radius * 2);

		// Draw text inside circle for label
		String label = Integer.toString(p);
		Font font = g.getFont();
		FontMetrics metrics = g.getFontMetrics(font);
		int labelWidth = metrics.stringWidth(label);
		int labelXCoord = xCoord + radius - (labelWidth / 2);
		int labelYCoord = yCoord + radius + (metrics.getAscent() / 2);
		g.setColor(Color.white);
		g.drawString(label, labelXCoord, labelYCoord);

		// Draw edge if necessary
		if (isAdd) {
			g.setColor(Color.BLACK);
			g.drawLine(x, y, point2.x, point2.y);
		}
	}

	/*
	 * 
	 * Hàm vẽ đường thẳng với mũi tên
	 * Input:
	 * - Graphics g: đối tượng Graphics để vẽ
	 * - int startX, startY: tọa độ điểm đầu
	 * - int endX, endY: tọa độ điểm cuối
	 * - int arrowHeadWidth: độ rộng của mũi tên
	 * - int arrowHeadHeight: độ cao của mũi tên
	 */
	public void drawArrowLine(Graphics g, int startX, int startY, int endX, int endY, int arrowHeadWidth,
			int arrowHeadHeight) {
		// Tính độ dài và góc của đường thẳng
		int deltaX = endX - startX;
		int deltaY = endY - startY;
		// double lineLength = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		double angle = Math.atan2(deltaY, deltaX);

		// Tính toán tọa độ của mũi tên
		double arrowAngle = Math.atan2(arrowHeadHeight, arrowHeadWidth);
		double arrowLength = Math.sqrt(arrowHeadHeight * arrowHeadHeight + arrowHeadWidth * arrowHeadWidth);
		double arrowX = endX - arrowLength * Math.cos(angle - arrowAngle);
		double arrowY = endY - arrowLength * Math.sin(angle - arrowAngle);
		double arrowX2 = endX - arrowLength * Math.cos(angle + arrowAngle);
		double arrowY2 = endY - arrowLength * Math.sin(angle + arrowAngle);

		// Vẽ đường thẳng
		if(setcolor)g.setColor(Color.RED);
		g.drawLine(startX, startY, endX, endY);

		
		// Vẽ mũi tên
		g.drawLine(endX, endY, (int) arrowX, (int) arrowY);
		g.drawLine(endX, endY, (int) arrowX2, (int) arrowY2);
		g.drawString(weight, (point1.x + point2.x + 10) / 2, (point1.y + point2.y) / 2 + 116);
	}

	// Hàm vẽ đường thẳng nối 2 đỉnh đồ thị vô hướng
	// Input:
	// - Graphics g: đối tượng Graphics để vẽ
	// - int startX, startY: tọa độ điểm đầu
	// - int endX, endY: tọa độ điểm cuối
	// - int labelDistance: khoảng cách giữa văn bản và đường thẳng
	// - int labelHeight: chiều cao của văn bản
	public void drawLineWithoutArrow(Graphics g, int startX, int startY, int endX, int endY, int labelDistance,
			int labelHeight) {
		if(setcolor)g.setColor(Color.RED);
		g.drawLine(startX, startY, endX, endY);
		g.drawString(weight, (point1.x + point2.x + 10) / 2, (point1.y + point2.y) / 2 + 116);
	}
	public void resetGraph(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, 600, 600);
	}
	public void eraseArrowLine(Graphics g, int startX, int startY, int endX, int endY, int arrowHeadWidth,
            int arrowHeadHeight) {
		if(setcolor)g.setColor(Color.RED);
    // Tính độ dài và góc của đường thẳng
		
    int deltaX = endX - startX;
    int deltaY = endY - startY;
    double angle = Math.atan2(deltaY, deltaX);

    // Tính toán tọa độ của mũi tên
    double arrowAngle = Math.atan2(arrowHeadHeight, arrowHeadWidth);
    double arrowLength = Math.sqrt(arrowHeadHeight * arrowHeadHeight + arrowHeadWidth * arrowHeadWidth);
    double arrowX = endX - arrowLength * Math.cos(angle - arrowAngle);
    double arrowY = endY - arrowLength * Math.sin(angle - arrowAngle);
    double arrowX2 = endX - arrowLength * Math.cos(angle + arrowAngle);
    double arrowY2 = endY - arrowLength * Math.sin(angle + arrowAngle);

    // Đặt màu nền là trắng
    g.setColor(Color.white);
    g.fillRect(startX, startY, endX - startX, endY - startY);

    // Xóa đường thẳng
    g.setColor(getBackground()); // Đặt màu nền của Graphics là màu nền của JPanel
    g.drawLine(startX, startY, endX, endY);

    // Xóa mũi tên
    g.setColor(getBackground()); // Đặt màu nền của Graphics là màu nền của JPanel
    g.drawLine(endX, endY, (int) arrowX, (int) arrowY);
    g.drawLine(endX, endY, (int) arrowX2, (int) arrowY2);

    // Vẽ lại nền
//    g.setColor(getBackground()); // Đặt màu nền của Graphics là màu nền của JPanel
//    g.fillRect(startX, startY, endX - startX, endY - startY);
}
	public void deleteEdge(Graphics g, int startX, int startY, int endX, int endY, int labelDistance, int labelHeight) {
	    Graphics2D g2d = (Graphics2D) g.create();
	    g2d.setColor(Color.WHITE);  // Thay đổi màu vẽ thành màu trắng để xóa cạnh
	    Stroke originalStroke = g2d.getStroke();
	    float strokeWidth = 1.0f;  // Độ dày nét vẽ (có thể điều chỉnh tùy ý)
	    BasicStroke newStroke = new BasicStroke(strokeWidth);
	    g2d.setStroke(newStroke);
	    g2d.drawLine(startX, startY, endX, endY);
	    g2d.setStroke(originalStroke);  // Khôi phục độ dày nét vẽ ban đầu
	    g2d.dispose();

	    // Xóa mũi tên
//	    double angle = Math.atan2(endY - startY, endX - startX);
//	    double arrowAngle = Math.atan2(arrowHeadHeight, arrowHeadWidth);
//	    double arrowLength = Math.sqrt(arrowHeadHeight * arrowHeadHeight + arrowHeadWidth * arrowHeadWidth);
//	    double arrowX = endX - arrowLength * Math.cos(angle - arrowAngle);
//	    double arrowY = endY - arrowLength * Math.sin(angle - arrowAngle);
//	    double arrowX2 = endX - arrowLength * Math.cos(angle + arrowAngle);
//	    double arrowY2 = endY - arrowLength * Math.sin(angle + arrowAngle);
//	    g.setColor(Color.WHITE);
//	    g.drawLine(endX, endY, (int) arrowX, (int) arrowY);
//	    g.drawLine(endX, endY, (int) arrowX2, (int) arrowY2);

	    // Xóa trọng số
	    g.setColor(Color.WHITE);
	    g.fillRect((point1.x + point2.x + 10) / 2 + 1, (point1.y + point2.y) / 2 + 100, 16, 16);
	}
	public void eraseWeight(Graphics g) {
		
		    if (point1 != null && point2 != null) {
		    	g.setColor(Color.WHITE);
//		    	g.drawString(weight, (point1.x + point2.x + 10) / 2, (point1.y + point2.y) / 2 + 116);
//		        g.setColor(Color.WHITE);
		        g.fillRect((point1.x + point2.x + 10) / 2 - 5, (point1.y + point2.y) / 2 + 101, 11, 11);
		    }
		
//	    g.setColor(Color.BLACK);
//	    g.drawLine(point1.x, point1.y, point2.x, point2.y);
	}

	/**
	 * @param setcolor the setcolor to set
	 */
	public void setSetcolor(boolean setcolor) {
		this.setcolor = setcolor;
	}
	
	

	

}
	    
	

	
