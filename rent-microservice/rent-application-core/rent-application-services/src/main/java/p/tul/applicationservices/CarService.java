package p.tul.applicationservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.tul.applicationports.repo.CarRepositoryPort;
import p.tul.applicationports.service.CreateCarUseCase;
import p.tul.domainmodel.entities.Car;

@Service
public class CarService implements CreateCarUseCase {
    private final CarRepositoryPort carRepository;

    @Autowired
    public CarService(CarRepositoryPort carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car createCar(double baseLoanPrice, String vin) {
        Car car = new Car(baseLoanPrice, vin);
        carRepository.addVehicle(car);
        return car;
    }
}
