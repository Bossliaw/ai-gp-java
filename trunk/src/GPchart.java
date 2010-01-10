import java.awt.*;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;

public class GPchart extends JFrame{
	
	public GPchart(int generation, int gen_max[], double gen_avg[]){
		//set Dataset
		CategoryDataset dataset = setDataset(generation, gen_max, gen_avg); 
		//creat chart
		JFreeChart chart = createChart(dataset);
		//get in chartpane and set size
		ChartPanel chartPanel = new ChartPanel(chart); 
		chartPanel.setPreferredSize(new Dimension(500, 270));
		//get in JFrame
		getContentPane().add(chartPanel); 
		pack(); 
		setVisible(true); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	} 
	
	
    private JFreeChart createChart(CategoryDataset dataset) {
		// TODO Auto-generated method stub
    	JFreeChart chart = ChartFactory.createLineChart( 
    			"GP Chart", // chart title 
    			"generation", // domain axis label 
    			"fitness", // range axis label 
    			dataset, // data 
    			PlotOrientation.VERTICAL, // orientation 
    			true, // include legend 
    			true, // tooltips
    			false // URLs? ); 
    			);
    	return chart; 

	}


	private CategoryDataset setDataset(int generation, int gen_max[], double gen_avg[]) {
		// TODO Auto-generated method stub
		//row
		String[] series = {"most","avg"};
		//columns--generation
		String[] category = new String[generation+1];
		for(int i=0;i<=generation;i++){
			category[i] = Integer.toString(i);
		}
		//data--mostfit--avgfit
		int[][] data = new int [category.length][series.length];
		for(int i=0; i<category.length; i++){
				data[i][0] = gen_max[i];
				data[i][1] = (int) gen_avg[i];
		}
		//creat dataset
		DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
		//get in data to dataset
		for(int i=0; i<category.length; i++){
			for(int j=0; j<series.length; j++){
			dataset.addValue(data[i][j], series[j], category[i]);	
			}
		}
		return dataset;
	}


	private String toStriog(int i) {
		// TODO Auto-generated method stub
		return null;
	}

}
