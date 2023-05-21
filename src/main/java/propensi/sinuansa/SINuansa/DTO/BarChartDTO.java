package propensi.sinuansa.SINuansa.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class BarChartDTO {
    ArrayList<String> labels;
    ArrayList<Long> data;
    ArrayList<Long> data2;

    public BarChartDTO(){
        this.labels = null;
        this.data = null;
        this.data2 = null;
    }

    public BarChartDTO(ArrayList<String> labels, ArrayList<Long> data, ArrayList<Long> data2){
        this.labels = labels;
        this.data = data;
        this.data2 = data2;
    }
}
