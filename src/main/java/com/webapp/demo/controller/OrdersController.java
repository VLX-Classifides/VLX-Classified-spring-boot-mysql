package com.webapp.demo.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.29.226:3000"})
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
	@GetMapping("/api/orders/{userid}")
	public ResponseModelList<Orders> getOrdersByBuyer(@PathVariable("userid") int id){
		List<Orders> ordersList = orderrepo.findByBuyerid(id);
		return new ResponseModelList<Orders>(true, "Buyer orders", ordersList);
	}


	// place order and save in Orders
	@PostMapping("/placeOrder")
	public ResponseModelParameter<Orders> placeOrder(@RequestBody OrderRequest orderreq)
	{
		// inserting data in orders table
		Orders order=new Orders();
		order.setBuyerid(orderreq.getBuyerid());
		List<Integer> prdtlist=orderreq.getPrdtids();
		String prdts=prdtlist.stream().map(String::valueOf).collect(Collectors.joining(","));
		order.setPrdtids(prdts);
		order.setPrice(orderreq.getPrice());

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		order.setCreateddate(dateFormat.format(date));

		orderrepo.save(order);
		
		//inserting data in payments table
		
		for(int i=0;i<prdtlist.size();i++)
		{
			// getting product details by product id
			Products product=new Products();
			product=productrepo.getById(prdtlist.get(i));
			
			// getting seller details by seller id from user table
			User seller=new User();
			seller=userrepo.getById(product.getCreatedby());
			
			Payments payment=new Payments();
			payment.setPrdtid(prdtlist.get(i));
			payment.setBuyerid(orderreq.getBuyerid());
			payment.setBuyercardno(orderreq.getBuyercardno());
			payment.setPrice(product.getPrice());
			payment.setSellerid(product.getCreatedby());
			payment.setSellercardno(seller.getCreditCard());
			paymentrepo.save(payment);
		}
		return new ResponseModelParameter<Orders>(true,"order placed",order);
	} 
	
	// rate order according to order id
	@PostMapping("/rateOrder")
	public ResponseModelParameter<Orders> rateOrder(@RequestBody RatingAndFeedback ratingandfeedback){
		Orders order=orderrepo.findById(ratingandfeedback.getOrderid()).orElse(null);
		order.setRating(ratingandfeedback.getRating());
		order.setFeedback(ratingandfeedback.getFeedback());
		orderrepo.save(order);
		return new ResponseModelParameter<Orders>(true, "order rated", order);
	}
}
