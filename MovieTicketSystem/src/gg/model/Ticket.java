
package gg.model;

/**
 * <h1>Ticket</h1>
 * 
 * The Ticket object for the ticket bought.
 * <p>
 * 
 * @author Chien-Wei-Chin
 * @version 1.0
 * @since 2017-06-25
 */
public class Ticket {
	private String ticketId;
	private String time;
	private String hall;
	private String seat;
	private String seatLeft;

	/**
	 * Getter that gets the ticket seat.
	 * 
	 * @return the ticket seat
	 */
	public String getSeat() {
		return seat;
	}

	/**
	 * Setter that sets the Seat location.
	 * 
	 * @param seat
	 *            the seat to be set.
	 */
	public void setSeat(String seat) {
		this.seat = seat;
	}

	/**
	 * Getter that gets the seat left number.
	 * 
	 * @return seat left number
	 */
	public String getSeatLeft() {
		return seatLeft;
	}

	/**
	 * Setter that sets the Seat left number.
	 * @param seatLeft
	 * 			the seat left number to be set
	 */
	public void setSeatLeft(String seatLeft) {
		this.seatLeft = seatLeft;
	}

	/**
	 * Getter that gets the ticketId.
	 * 
	 * @return the ticket's Id
	 */
	public String getTicketId() {
		return ticketId;
	}
	/**
	 * Setter that sets the ticket id
	 * @param ticketId
	 * 			the ticket id to be set
	 */
	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	/**
	 * Getter that gets the seat movie time.
	 * 
	 * @return the ticket's movie time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * Setter that sets the ticket time
	 * @param time
	 * 			the time to be set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * Getter that gets the ticket's hall
	 * 
	 * @return ticket's hall
	 */
	public String getHall() {
		return hall;
	}
	/**
	 * Getter that set the ticket hall
	 * @param hall
	 * 			the hall to be set
	 */
	public void setHall(String hall) {
		this.hall = hall;
	}

}
