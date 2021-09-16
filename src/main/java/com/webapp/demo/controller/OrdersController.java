package com.webapp.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.webapp.demo.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.webapp.demo.repo.OrdersRepo;
import com.webapp.demo.repo.PaymentsRepo;
import com.webapp.demo.repo.ProductsRepo;
import com.webapp.demo.repo.UserRepo;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class OrdersController {
	@Autowired
	OrdersRepo orderrepo;
	
	@Autowired
	ProductsRepo productrepo;
	
	@Autowired
	PaymentsRepo paymentrepo;

	@Autowired
	UserRepo userrepo;


	//get user specific orders
	@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.29.226:3000"})
	@GetMapping("/api/orders/{userid}")
	public ResponseModelList<Orders> getOrdersByBuyer(@PathVariable("userid") int id){
		List<Orders> ordersList = orderrepo.findByBuyerid(id);
		return new ResponseModelList<Orders>(true, "Buyer orders", ordersList);
	}


	// place order and save in Orders
	@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.29.226:3000"})
	@PostMapping("/placeOrder")
	public ResponseModelParameter<Orders> placeOrder(@RequestBody OrderRequest orderreq){
		// inserting data in orders table
		Orders order = new Orders();
		order.setBuyerid(orderreq.getBuyerid());
		List<Integer> prdtlist = orderreq.getPrdtids();
		String prdts = prdtlist.stream().map(String::valueOf).collect(Collectors.joining(","));
		order.setPrdtids(prdts);
		order.setPrice(orderreq.getPrice());
		orderrepo.save(order);
		
		//inserting data in payments table
		
		for(int i=0;i<prdtlist.size();i++){
			// getting product details by product id
			Products product=new Products();
			product=productrepo.getById(prdtlist.get(i));
			
			// getting seller details by seller id from user table
			User seller=new User();
			seller=userrepo.getById(product.getCreatedby());
			
			Payments payment=new Payments();
			payment.setBuyerid(orderreq.getBuyerid());
			payment.setBuyercardno(orderreq.getBuyercardno());
			payment.setPrice(product.getPrice());
			payment.setSellerid(product.getCreatedby());
			payment.setSellercardno(seller.getAcdetail());
			paymentrepo.save(payment);
		}
		return new ResponseModelParameter<Orders>(true,"order placed",order);
	} 
	
	// rate order according to order id
	@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.29.226:3000"})
	@PostMapping("/rateOrder")
	public ResponseModelParameter<Orders> rateOrder(@RequestBody RatingAndFeedback ratingandfeedback){
		Orders order=orderrepo.findById(ratingandfeedback.getOrderId()).orElse(null);
		order.setRating(ratingandfeedback.getRating());
		order.setFeedback(ratingandfeedback.getFeedback());
		orderrepo.save(order);
		return new ResponseModelParameter<Orders>(true, "order rated", order);
	}
}
