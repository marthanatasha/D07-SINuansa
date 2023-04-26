package propensi.sinuansa.SINuansa.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class LineChartDTO {
    ArrayList<Integer> labels;
    ArrayList<Long> data;

    public LineChartDTO(){
        this.labels = null;
        this.data = null;
    }

    public LineChartDTO(ArrayList<Integer> labels, ArrayList<Long> data){
        this.labels = labels;
        this.data = data;
    }
}
