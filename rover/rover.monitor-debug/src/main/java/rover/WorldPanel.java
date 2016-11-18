package rover;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

/**
 * Created by suegy on 10/08/15.
 */
public class WorldPanel extends JPanel {

    private MonitorInfo monitorInfo = null;

    private int scale = 10;

    private Boolean showScans = false;

    private ArrayList<Double> _scanPoints = new ArrayList<Double>();

    public void addScanPoint(double x, double y, double range) {
	_scanPoints.add(x);
	_scanPoints.add(y);
	_scanPoints.add(range);
    }

    public void ToggleShowScans(Boolean value) {
	showScans = value;
    }

    public void clearScanPoints() {
	_scanPoints.clear();
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;

        if(monitorInfo != null) {
            this.setSize(monitorInfo.getWidth() * scale, monitorInfo.getHeight() * scale);
            this.setPreferredSize(new Dimension(monitorInfo.getWidth() * scale, monitorInfo.getHeight() * scale));
        }
    }

    public MonitorInfo getMonitorInfo() {
        return monitorInfo;
    }

    public void setMonitorInfo(MonitorInfo monitorInfo) {
        this.monitorInfo = monitorInfo;

        this.setSize(monitorInfo.getWidth() * scale, monitorInfo.getHeight() * scale);
        this.setPreferredSize(new Dimension(monitorInfo.getWidth() * scale, monitorInfo.getHeight() * scale));

        this.repaint();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        if(monitorInfo != null ) {

            g2.setPaint(Color.WHITE);
            g2.fill(new Rectangle2D.Double(0,0, monitorInfo.getWidth() * scale, monitorInfo.getHeight() * scale));

            g2.setPaint(Color.RED);
            for(MonitorInfo.Team t : monitorInfo.getTeams()) {
                g2.fill( new Rectangle2D.Double(t.getX() * scale, t.getY() * scale, 10 + scale, 10 + scale));
            }

            for(MonitorInfo.Resource r : monitorInfo.getResources()) {
            g2.setPaint(Color.BLUE);
		if (r.getType() == 1) { g2.setPaint(Color.BLUE); }
		if (r.getType() == 2) { g2.setPaint(Color.CYAN); }
                g2.fill( new RoundRectangle2D.Double(r.getX() * scale, r.getY() * scale, 10 + scale, 10 + scale, 2 ,2 ));
            }

            g2.setPaint(Color.GREEN);
            for(MonitorInfo.Rover r : monitorInfo.getRovers()) {
                g2.fill( new Ellipse2D.Double(r.getX() * scale, r.getY() * scale, 10 + scale,10 +scale));
            }

	    if (showScans) {
		    g2.setPaint(Color.BLACK);
		    for (int i = 0; i < _scanPoints.size(); i+=3) {
			double radius = 2.0*scale*_scanPoints.get(i+2);
			double tlX = scale + _scanPoints.get(i)*scale - radius;
			double tlY = scale + _scanPoints.get(i+1)*scale - radius;
			g2.draw(new Ellipse2D.Double(tlX, tlY, 2*radius, 2*radius));
			//for (int j = 1; j < 8; j++) {
			    //g2.draw(new Ellipse2D.Double(_scanPoints.get(i)*scale, _scanPoints.get(i+1)*scale, 2*(10 + scale)*_scanPoints.get(i+2), 2*(10 + scale)*_scanPoints.get(i+2)));
			//}
		    }
	    }

        }

    }
}
