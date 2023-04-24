package com.driver.services.impl;

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

import java.util.ArrayList;
import java.util.List;

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
		Customer customer=customerRepository2.findById(customerId).get();
		customerRepository2.delete(customer);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		TripBooking tripBooking=new TripBooking();
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);
		tripBooking.setDistanceInKm(distanceInKm);

		Customer customer=customerRepository2.findById(customerId).get();
		tripBooking.setCustomer(customer);

		List<Driver> driverList=new ArrayList<>();
		driverList=driverRepository2.findAll();

		int smallestValue=Integer.MAX_VALUE;
		Driver finalDriver=null;
		for(Driver driver:driverList){
			if(driver.getCab().getAvailable()) {
				if (driver.getDriverId() < smallestValue) {
					smallestValue = driver.getDriverId();
					finalDriver=driver;
				}
			}
		}
		if (finalDriver==null){
			throw new Exception("No cab available!");
		}
		tripBooking.setDriver(finalDriver);
		tripBooking.setStatus(TripStatus.CONFIRMED);
		tripBooking.setBill(distanceInKm*finalDriver.getCab().getPerKmRate());
		tripBookingRepository2.save(tripBooking);
		return tripBooking;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly

		TripBooking tripBooking=tripBookingRepository2.findById(tripId).get();
		tripBooking.setStatus(TripStatus.CANCELED);
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly

		TripBooking tripBooking=tripBookingRepository2.findById(tripId).get();
		tripBooking.setStatus(TripStatus.COMPLETED);
	}
}
