public class Axon {
    double weight; //how much the axon modifies the value from the previous layer, 0 to 1
    Axon(double tweight){ //constructor if given a predefined weight
        weight = tweight;
    }
    Axon(){ //constructor if not given a predefined weight
        weight = Math.random() * 2 - 1; //-1 to 1
    }
    void mod(double delta){
        weight += delta;
    }
}
//try graphing the backprop function to see if its between 0 and 1
