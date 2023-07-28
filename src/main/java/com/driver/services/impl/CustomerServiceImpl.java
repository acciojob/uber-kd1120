package com.driver.services.impl;

import com.driver.Exception.CabNotAvailableException;
import com.driver.Exception.CustomerNotFoundException;
import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		if (customerRepository2.existsById(customerId)){
			Customer customer = customerRepository2.findById(customerId).get();
			customerRepository2.delete(customer);
		}
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		Customer customer;
		try {
			customer = customerRepository2.findById(customerId).get();
		} catch (Exception e){
			throw new CabNotAvailableException("No cab available!");
		}
		TripBooking tripBooking = new TripBooking();
		List<Driver> driverList=driverRepository2.findAll();
		driverList.sort((a,b)->(a.getDriverId()-b.getDriverId()));
		for (Driver driver:driverList) {
			if (driver.getCab().getAvailable()){
				driver.getCab().setAvailable(false);

				tripBooking.setFromLocation(fromLocation);
				tripBooking.setToLocation(toLocation);
				tripBooking.setDistanceInKm(distanceInKm);
				tripBooking.setStatus(TripStatus.CONFIRMED);
				tripBooking.setBill(distanceInKm*driver.getCab().getPerKmRate());

				tripBooking.setDriver(driver);
				tripBooking.setCustomer(customer);
//				TripBooking savedTripBooking = tripBookingRepository2.save(tripBooking);
				customer.getTripBookingList().add(tripBooking);
				driver.getTripBookingList().add(tripBooking);

				customerRepository2.save(customer);
				driverRepository2.save(driver);

				return tripBooking;
			}
		}
		throw new CabNotAvailableException("No cab available!");
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking> optionalTripBooking = tripBookingRepository2.findById(tripId);
		if (optionalTripBooking.isPresent()){
			TripBooking tripBooking=optionalTripBooking.get();
			tripBooking.setStatus(TripStatus.CANCELED);
			tripBooking.setBill(0);

			Driver driver=tripBooking.getDriver();
			driver.getCab().setAvailable(true);

			driverRepository2.save(driver);
			tripBookingRepository2.save(tripBooking);
		}
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking> optionalTripBooking = tripBookingRepository2.findById(tripId);
		if (optionalTripBooking.isPresent()){
			TripBooking tripBooking=optionalTripBooking.get();
			tripBooking.setStatus(TripStatus.COMPLETED);

			Driver driver=tripBooking.getDriver();
			driver.getCab().setAvailable(true);

			driverRepository2.save(driver);
			tripBookingRepository2.save(tripBooking);
		}
	}
}