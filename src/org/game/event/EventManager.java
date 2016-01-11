package org.game.event;

import java.util.concurrent.TimeUnit;

import org.game.Game;

/**
 * A class that manages <code>Event</code>s for a specific
 * <code>GameEngine</code>.
 *
 * @author Graham Edgecombe
 */
public class EventManager {

	/**
	 * The <code>GameEngine</code> to manager events for.
	 */
	private Game game;

	/**
	 * Creates an <code>EventManager</code> for the specified
	 * <code>GameEngine</code>.
	 *
	 * @param engine The game engine the manager is managing events for.
	 */
	public EventManager(Game game) {
		this.game = game;
	}

	/**
	 * Submits a new event to the <code>GameEngine</code>.
	 *
	 * @param event The event to submit.
	 */
	public void submit(final Event event) {
		submit(event, event.getDelay());
	}

	/**
	 * Schedules an event to run after the specified delay.
	 *
	 * @param event The event.
	 * @param delay The delay.
	 */
	private void submit(final Event event, final long delay) {
		game.scheduleLogic(new Runnable() {
			public void run() {
				long start = System.currentTimeMillis();
				if(event.isRunning()) { // this must be false, however
					try {
						event.execute(); // doesnt execute this
					} catch(Exception e) {
						e.printStackTrace();
					}
				} else {
					return;
				}
				long elapsed = System.currentTimeMillis() - start;
				long remaining = event.getDelay() - elapsed;
				if(remaining <= 0) {
					remaining = 0;
				}
				submit(event, remaining);
			}
		}, delay, TimeUnit.MILLISECONDS);
	}


}
