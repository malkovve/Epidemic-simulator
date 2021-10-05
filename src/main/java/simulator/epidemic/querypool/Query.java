package simulator.epidemic.querypool;

import simulator.epidemic.objects.InputData;

public abstract class Query<T> {

    public abstract void prepare(InputData inputData);
}
