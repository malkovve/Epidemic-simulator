package simulator.epidemic.utils;

import simulator.epidemic.objects.animation.InputData;

public class ObjectValidator {

    public static InputData validateInputData(
            String meshSize,
            String quantityPeople,
            String illPeople
    ) throws Throwable {
        String[] strMesh = meshSize.split("x");
        if (strMesh.length != 2) {
            throw new NumberFormatException("example mesh input: NxN");
        }
        if (quantityPeople.length() == 0 || illPeople.length() == 0) {
            throw new NumberFormatException(null);
        }
        try {
            switch (strMesh.length) {
                case 1: {
                    break;
                }
                case 2: {
                    int meshSizeX = strMesh[0].length() != 0 ? Integer.parseInt(strMesh[0]) : 0;
                    int meshSizeY = strMesh[1].length() != 0 ? Integer.parseInt(strMesh[1]) : 0;
                    int quantityAllPeople = Integer.parseInt(quantityPeople);
                    int quantityIllPeople = Integer.parseInt(illPeople);

                    if (meshSizeX == 0 || meshSizeY == 0) {
                        throw new NumberFormatException("grid size cannot be 0x0");
                    }
                    if (quantityAllPeople <= quantityIllPeople) {
                        throw new NumberFormatException("the number of people is less than the number of patients");
                    }
                    break;
                }
            }
        } catch (Throwable e) {
            throw e;
        }
        return null;
    }
}
