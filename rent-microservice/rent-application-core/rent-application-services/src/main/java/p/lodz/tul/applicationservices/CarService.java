package p.lodz.tul.applicationservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.lodz.tul.applicationports.repo.CarRepositoryPort;
import p.lodz.tul.applicationports.service.CreateCarUseCase;
import p.lodz.tul.applicationports.service.UpdateCarUseCase;
import p.lodz.tul.domainmodel.entities.Car;

@Service
public class CarService implements CreateCarUseCase, UpdateCarUseCase {
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

    @Override
    public Car updateCar(Car car) {
        carRepository.updateVehicle(car.getVin(), car);
        return car;
    }
}
