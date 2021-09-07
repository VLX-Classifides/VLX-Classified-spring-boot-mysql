package com.webapp.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.webapp.demo.model.OrderRequest;
import com.webapp.demo.model.Orders;
import com.webapp.demo.model.Payments;
import com.webapp.demo.model.Products;
import com.webapp.demo.model.User;
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
	// place order and save in Orders
	@Autowired
	UserRepo userrepo;
	@PostMapping("/placeOrder")
	public ResponseEntity<Orders> placeOrder(@RequestBody OrderRequest orderreq)
	{
		// inserting data in orders table
		Orders order=new Orders();
		order.setBuyerid(orderreq.getBuyerid());
		List<Integer> prdtlist=orderreq.getPrdtids();
		String prdts=prdtlist.stream().map(String::valueOf).collect(Collectors.joining(","));
		order.setPrdtids(prdts);
		order.setPrice(orderreq.getPrice());
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
			payment.setBuyerid(orderreq.getBuyerid());
			payment.setBuyercardno(orderreq.getBuyercardno());
			payment.setPrice(product.getPrice());
			payment.setSellerid(product.getCreatedby());
			payment.setSellercardno(seller.getAcdetail());
			paymentrepo.save(payment);
		}
		return ResponseEntity.ok(order);
	} 
}
