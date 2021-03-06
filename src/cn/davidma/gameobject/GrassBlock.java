package cn.davidma.gameobject;

import cn.davidma.Minesweeper;
import cn.davidma.neat.application.InputManager;
import cn.davidma.neat.application.InputManager.MouseEvent;
import cn.davidma.neat.application.InputManager.MouseKey;
import cn.davidma.neat.object.GameObject;
import cn.davidma.neat.util.MathUtil;
import cn.davidma.neat.util.PathUtil;
import javafx.scene.effect.ColorAdjust;

public class GrassBlock extends GameObject {
	
	public static final int FALL_LENGTH = 40;
	private static final ColorAdjust DARK = MathUtil.convColorAdjust(85, 45, 34, 1);
	private static final ColorAdjust LIGHT = MathUtil.convColorAdjust(85, 45, 36, 1);
	private static final ColorAdjust HOVER = MathUtil.convColorAdjust(85, 45, 38, 1);
	
	private int x;
	private int y;
	private boolean dark;
	private Flag flag;
	
	// Used for fall off aimation.
	private int falling;
	private double horizontal;
	private double vertical;
	
	public GrassBlock(int x, int y, boolean dark) {
		super();
		this.x = x;
		this.y = y;
		this.dark = dark;
		this.falling = -1;
	}
	
	@Override
	public void start() {
		this.setScaleX(Minesweeper.CELL_SIZE);
		this.setScaleY(Minesweeper.CELL_SIZE);
		this.setImage(PathUtil.fromRelativePath(Minesweeper.class, "resources/images/square.png"));
		this.setColorEffect(this.dark ? DARK : LIGHT);
	}

	@Override
	public void update() {
		if (this.falling >= 0) {
			this.moveX((int) this.horizontal);
			this.moveY((int) Math.min(this.vertical += 0.5, 10));
			this.rotate(this.horizontal);
			this.enlarge(0.94);
			if (--this.falling == 0) {
				this.removeFromScene();
			}
		}
	}
	
	@Override
	public void onClick(MouseEvent mouseEvent) {
		if (falling != -1) return;
		
		if (mouseEvent.mouseKey == MouseKey.LEFT && !InputManager.isKeyDown("SHIFT")) {
			if (this.flag == null) Minesweeper.processClick(this.x, this.y);
		} else {
			if (this.flag == null) {
				if (Minesweeper.flagLeft > 0) {
					this.flag = new Flag();
					this.flag.setX(this.getX());
					this.flag.setY(this.getY());
					this.getParentScene().addChild(flag);
					Minesweeper.all.addChild(flag);
					Minesweeper.flagLeft--;
					Minesweeper.updateFlagText();
				}
			} else {
				this.flag.setClick();
				this.flag = null;
				Minesweeper.flagLeft++;
				Minesweeper.updateFlagText();
			}
		}
	}
	
	@Override
	public void onMouseEnter() {
		if (this.falling < 0) this.setColorEffect(HOVER);
	}
	
	@Override
	public void onMouseExit() {
		if (this.falling < 0) this.setColorEffect(this.dark ? DARK : LIGHT);
	}
	
	public void setClick() {
		this.bringToFront();
		if (this.flag != null) this.flag.setClick();
		this.falling = FALL_LENGTH;
		int force = Minesweeper.rand.nextInt(6);
		this.horizontal = force - 3;
		this.vertical = -force - 3;
	}
}
