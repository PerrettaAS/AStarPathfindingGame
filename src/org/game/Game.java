package org.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import org.game.entity.MouseInput;
import org.game.entity.Player;
import org.game.event.Event;
import org.game.event.EventManager;
import org.game.gfx.Screen;
import org.game.gfx.SpriteSheet;
import org.game.map.Map;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private Screen screen;
	
	public boolean running = false;
	
	public int tickCount = 0;
	
	public Map map = new Map("home");
	public Player player;
	
	private final ScheduledExecutorService logicService = Executors.newScheduledThreadPool(1);
	
	private BufferedImage image = new BufferedImage(Constants.WIDTH, Constants.HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	public Game() {
		MouseInput mouse = new MouseInput();
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		setMinimumSize(new Dimension(Constants.WIDTH * Constants.SCALE, Constants.HEIGHT * Constants.SCALE));
		setMaximumSize(new Dimension(Constants.WIDTH * Constants.SCALE, Constants.HEIGHT * Constants.SCALE));
		setPreferredSize(new Dimension(Constants.WIDTH * Constants.SCALE, Constants.HEIGHT * Constants.SCALE));
		frame = new JFrame(Constants.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		player = new Player();
	}
	
	public void init() {
		screen = new Screen(Constants.WIDTH, Constants.HEIGHT, new SpriteSheet("./res/sprite_sheet.png"));
	}
	
	public synchronized void start() {
		running = true;
		new Thread(this).start();
	}
	
	public synchronized void stop() {
		running = false;
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D/60D;
		int frames = 0;
		int ticks = 0;
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		boolean shouldRender = false;
		init();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			while(delta >= 1) {
				ticks++;
				tick();
				delta--;
				shouldRender = true;
			}
			try {
				Thread.sleep(2);
			} catch(Exception e) {
				e.printStackTrace();
			}
			if(shouldRender) {
				frames++;
				render();
			}
			if(System.currentTimeMillis() - lastTimer > 1000) {
				lastTimer += 1000;
				frames = 0;
				ticks = 0;
			}
		}
	}
	
	public void tick() {
		tickCount++;
		screen.xOffset++;
		screen.yOffset++;
	}
	
	public BufferStrategy bufferStrategy = getBufferStrategy();
	
	public void render() {
		bufferStrategy = getBufferStrategy();
		if(bufferStrategy == null) {
			createBufferStrategy(3);
			return;
		}
		screen.render(pixels, 0, Constants.WIDTH);
		Graphics graphics = bufferStrategy.getDrawGraphics();
		map.drawMap(graphics);
		player.drawPlayer(graphics);
		graphics.dispose();
		bufferStrategy.show();
	}
	
	private EventManager eventManager = new EventManager(this);
	
	public void submit(Event event) {
        if(eventManager == null || event == null)
            return;
        this.eventManager.submit(event);
    }
	
	public ScheduledFuture<?> scheduleLogic(final Runnable runnable, long delay, TimeUnit unit) {
		return logicService.schedule(new Runnable() {
			public void run() {
				try {
					runnable.run();
				} catch(Exception e) {
					System.out.println("Server shit happening 1");
					e.printStackTrace();
				}
			}
		}, delay, unit);
	}

}
