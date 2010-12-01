

import java.util.List;

import no.conduct.totalconquest.AbstractTank;
import no.conduct.totalconquest.Direction;

public class ThinkTank extends AbstractTank {

	private Direction lastHitState;

	@Override
	public void go() {
		if(isUnableToMove()){
			if (isInFight()) {
				hitAndRememberEnemy();
				return;
			}
		}

		if (canReachPreviousEnemy()) {
			hit(lastHitState);
			return;
		} 

		if (isInFight()) {		
			hitAndRememberEnemy();
			return;
		}
		
		move(followOrMoveRandomly());
	}

	private void hitAndRememberEnemy() {
		Direction enemyPosition = getEnemyPosition();
		hit(enemyPosition);
		lastHitState = enemyPosition;
	}

	private Direction followOrMoveRandomly() {
		if (lastHitState != null && isFree(lastHitState)) {
			return lastHitState;
		} else {
		return getRandomDirection();
		}
	}

	private boolean isUnableToMove() {
		for (Direction direction : Direction.values()) {
			if (isFree(direction)) {
				return false;
			}
		}
		return true;
	}

	private boolean canReachPreviousEnemy() {
		return lastHitState != null && isFoe(lastHitState);
	}

	private Direction getRandomDirection() {
		Direction direction = Direction.any();
		while(!isFree(direction)) {
			direction = Direction.any();
		}
		return direction;
	}

}
