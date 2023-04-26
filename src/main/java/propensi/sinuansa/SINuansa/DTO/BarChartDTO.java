package propensi.sinuansa.SINuansa.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class BarChartDTO {
    ArrayList<String> labels;
    ArrayList<Long> data;

    public BarChartDTO(){
        this.labels = null;
        this.data = null;
    }

    public BarChartDTO(ArrayList<String> labels, ArrayList<Long> data){
        this.labels = labels;
        this.data = data;
    }
}
