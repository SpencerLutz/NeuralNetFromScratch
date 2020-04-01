class MLP {
    Axon[][][] axons; //array of axons with weights ([layer][receiving][sending])
    Neuron[][] hidden; //array of hidden layers ([layer][neuron])
    private double learnRate;
    MLP(Axon[][][] tAxons, Neuron[][] tHidden, double tLearnRate){ //constructor if neurons and axons provided
        axons = tAxons;
        hidden = tHidden;
        learnRate = tLearnRate;
    }
    MLP(int iSize, int hSize, int hNums, int oSize){    //constructor to make random neurons
        axons = randAxons(iSize, hSize, hNums, oSize);  //and axons for the MLP
        hidden = randHiddens(hSize, hNums);
        learnRate = .9;
    }
    Neuron[] think(double[] input, Neuron[] output){ //calculates output values
        for(int i = 0; i < hidden.length; i++){ //for each hidden layer
            if(i == 0) for(int j = 0; j < hidden[i].length; j++) //if its the first layer
                hidden[i][j].calc(sum(input, axons[i][j])); //multiply input values
            else for(int j = 0; j < hidden.length; j++) //any other time
                hidden[i][j].calc(sum(hidden[i-1], axons[i][j]));   //multiply the previous
        }                                                           //hidden layer
        for(int i = 0; i < output.length; i++) //for each output neuron
            output[i].calc(sum(hidden[hidden.length-1], axons[axons.length-1][i]));
        return output; //multiply the last hidden layer with last axons and return
    }
    double sum(double[] a, Axon[] b){      //gets the sum total of all values feeding into
        int sum = 0;                       //a neuron, where a is a double[] of all values
        for(int k = 0; k < a.length; k++)  //in the previous layer, and b is an array of
            sum += a[k] * b[k].weight;     //all axons feeding from the a's to this neuron
        return sum+b[a.length].weight;     //apply bias
    }
    double sum(Neuron[] a, Axon[] b){      //gets the sum total of all values feeding into
        int sum = 0;                       //a neuron, where a is a Neuron[] of all neurons
        for(int k = 0; k < a.length; k++)  //in the previous layer, and b is an array of
            sum += a[k].value*b[k].weight; //all axons feeding from the a's to this neuron
        return sum+b[a.length].weight;     //apply bias
    }
    Axon[][][] randAxons(int iSize, int hSize, int hNums, int oSize){ //generates axons with
        Axon[][][] ret = new Axon[hNums+1][][];                       //random weights
        for(int i = 0; i < ret.length; i++) { //for each axon layer
            ret[i] = new Axon[i == hNums ? oSize : hSize][]; //if last, set ret[i] to a new
            for (int j = 0; j < ret[i].length; j++) {        //array of axons of size of output
                ret[i][j] = new Axon[i == 0 ? iSize+1 : hSize+1];//if first, set ret[i][j] to a new
                for (int k = 0; k < ret[i][j].length; k++)   //array of axons of size of input
                    ret[i][j][k] = new Axon(); //-1 to 1
            }
        }
        return ret;
    }
    Neuron[][] randHiddens(int size, int num){  //generates hidden neurons with random biases
        Neuron[][] ret = new Neuron[num][size]; //return array ([layers][neurons])
        for(int i = 0; i < num; i++) //for each layer
            for(int j = 0; j < size; j++) //for each neuron
                ret[i][j] = new Neuron(); //create neuron
        return ret;
    }
    void backPropagate(double[] exp, double[] inputx, Neuron[] outputx){
        double error = 0; //error value
        for(int i = 0; i < outputx.length; i++) //loop through output values
            error += exp[i]-outputx[i].value; //find error (probably change this)
        error /= outputx.length; //get mean of values
        for(int i = 0; i < outputx.length; i++){ //loop through all output nodes
            double o = outputx[i].value; //output value of this node
            for(int j = 0; j < hidden[hidden.length-1].length; j++) { //loop through last hidden nodes
                double p = hidden[hidden.length-1][j].value; //output of this node
                axons[hidden.length][i][j].mod(learnRate*error*p*o*(1-o));
            }
            axons[hidden.length][i][hidden[hidden.length-1].length].mod(learnRate*error*o*(1-o));
        }
        for(int i = hidden.length-1; i > 0; i--) //for each hidden layer
            for(int j = 0; j < hidden[i].length; j++){ //for each node in layer
                double o = hidden[i][j].value; //output value of this node
                for(int k = 0; k < hidden[i-1].length; k++){ //loop through previous hidden nodes
                    double p = hidden[i-1][k].value; //output of previous hidden node
                    axons[i][j][k].mod(learnRate*error*p*o*(1-o));
                }
                axons[i][j][hidden[i-1].length].mod(learnRate*error*o*(1-o));
            }
        for(int i = 0; i < hidden[0].length; i++){
            double o = hidden[0][i].value;
            for(int j = 0; j < inputx.length; j++){
                double p = inputx[j];
                axons[0][i][j].mod(learnRate*error*p*o*(1-o));
            }
            axons[0][i][inputx.length].mod(learnRate*error*o*(1-o));
        }
        for(int i = 0; i < axons.length; i++)
            for(int j = 0; j < axons[i].length; j++){
                for(int k = 0; k < axons[i][j].length; k++)
                    System.out.println(axons[i][j][k].weight);
            }
    }
}
