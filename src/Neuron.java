class Neuron {
    double value; //value is strength of neuron, bias shifts activation curve
    Neuron(){}
    void calc(double sum){ //sigmoid activation that adds the bias as well
        value = 1/(1+Math.exp((sum)*-1));
    }
}
