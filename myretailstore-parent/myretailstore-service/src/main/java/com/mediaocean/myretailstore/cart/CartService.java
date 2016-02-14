package com.mediaocean.myretailstore.cart;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

public interface CartService {

	@GET
    @Path("/new")
	@Produces("application/json")
	long createNewCartId();

	@GET
    @Path("/{cartId}")
	@Produces("application/json")
	CartDto fetchCartDetails(@PathParam("cartId") long cartId);
	
	@POST
    @Path("/{cartId}/update")
	@Consumes("application/json")
	void updateCartItem(@PathParam("cartId") long cartId, CartItemDto cartItem);
	
	@GET
    @Path("/{cartId}/checkout")
	@Produces("application/json")
	CartBillDto checkoutCart(@PathParam("cartId") long cartId);

}
