package cn.davidma.gameobject;

import cn.davidma.Minesweeper;
import cn.davidma.neat.object.GameObject;
import cn.davidma.neat.util.PathUtil;

public class Mine extends GameObject {

	@Override
	public void start() {
		this.setImage(PathUtil.fromRelativePath(Minesweeper.class, "resources/images/mine.png"));
		this.setScaleX(Minesweeper.CELL_SIZE * 0.5D);
		this.setScaleY(Minesweeper.CELL_SIZE * 0.5D);
	}

	@Override
	public void update() {
		
	}
}
