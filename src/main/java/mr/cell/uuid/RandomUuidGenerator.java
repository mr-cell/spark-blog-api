package mr.cell.uuid;

import java.util.UUID;

public class RandomUuidGenerator implements UuidGenerator {

	@Override
	public UUID generate() {
		return UUID.randomUUID();
	}

}
