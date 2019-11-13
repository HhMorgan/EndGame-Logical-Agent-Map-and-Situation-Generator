import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

public class Main {

	public static void printGrid(String grid) {
		String[] parsedString = grid.split(";");
		String[] sizeString = parsedString[0].split(",");
		String[] characterString = parsedString[1].split(",");
		String[] villainString = parsedString[2].split(",");
		String[] collectableString = parsedString[3].split(",");
		byte m = Byte.parseByte(sizeString[0].trim(), 10);
		byte n = Byte.parseByte(sizeString[1], 10);
		char[][] gridVis = new char[m][n];
		for (char[] row : gridVis)
			Arrays.fill(row, '_');
		byte ix = Byte.parseByte(characterString[0], 10);
		byte iy = Byte.parseByte(characterString[1], 10);
		gridVis[ix][iy] = 'I';
		byte tx = Byte.parseByte(villainString[0], 10);
		byte ty = Byte.parseByte(villainString[1], 10);
		gridVis[tx][ty] = 'T';
		for (int i = 0; i < collectableString.length - 1; i += 2) {
			byte sx = Byte.parseByte(collectableString[i], 10);
			byte sy = Byte.parseByte(collectableString[i + 1], 10);
			gridVis[sx][sy] = 'S';
		}
		for (char[] row : gridVis) {
			System.out.println(Arrays.toString(row));
		}
	}

	public static int checkIndex(byte[] encoding) {
		if (encoding.length > 5) {
			for (int i = 6; i + 1 < encoding.length; i += 2) {
				if (encoding[2] == encoding[i] && encoding[3] == encoding[i + 1]) {
					return i;
				}
			}
		}
		return -1;
	}

	public static Node collect(Node node) {
		byte[] encoding = node.grid;
		int stoneIndex = checkIndex(encoding);
		if (stoneIndex != -1) {
			byte[] newEncoding = new byte[encoding.length - 2];
			int newEncodingIndex = 0;
			for (int i = 0; i + 1 < encoding.length; i += 2) {
				if (i != stoneIndex) {
					newEncoding[newEncodingIndex] = encoding[i];
					newEncoding[newEncodingIndex + 1] = encoding[i + 1];
					newEncodingIndex += 2;
				}
			}
			return new Node("c," + node.sequence, newEncoding);
		}
		return null;
	}

	public static Node up(Node node) {
		byte[] encoding = node.grid;
		byte[] newEncoding = new byte[encoding.length];
		if (encoding[2] > 0) {
			newEncoding[2] = (byte) (encoding[2] - 1);
			newEncoding[3] = encoding[3];

			for (int i = 0; i + 1 < encoding.length; i += 2) {
				if (i != 2) {
					newEncoding[i] = encoding[i];
					newEncoding[i + 1] = encoding[i + 1];
				}
			}
			if (newEncoding.length == 6
					|| (newEncoding.length >= 8 && !(newEncoding[2] == newEncoding[4] && newEncoding[3] == newEncoding[5])))
				return new Node("u,"+ node.sequence, newEncoding);

		}
		return null;
	}

	public static Node down(Node node) {
		byte[] encoding = node.grid;
		byte[] newEncoding = new byte[encoding.length];
		if (encoding[2] + 1 < encoding[0]) {
			newEncoding[2] = (byte) (encoding[2] + 1);
			newEncoding[3] = encoding[3];

			for (int i = 0; i + 1 < encoding.length; i += 2) {
				if (i != 2) {
					newEncoding[i] = encoding[i];
					newEncoding[i + 1] = encoding[i + 1];
				}
			}
			if (newEncoding.length == 6
					|| (newEncoding.length >= 8 && !(newEncoding[2] == newEncoding[4] && newEncoding[3] == newEncoding[5])))
			return new Node("d," + node.sequence, newEncoding);

		}
		return null;
	}

	public static Node left(Node node) {
		byte[] encoding = node.grid;
		byte[] newEncoding = new byte[encoding.length];
		if (encoding[3] > 0) {
			newEncoding[2] = encoding[2];
			newEncoding[3] = (byte) (encoding[3] - 1);

			for (int i = 0; i + 1 < encoding.length; i += 2) {
				if (i != 2) {
					newEncoding[i] = encoding[i];
					newEncoding[i + 1] = encoding[i + 1];
				}
			}
			if (newEncoding.length == 6
					|| (newEncoding.length >= 8 && !(newEncoding[2] == newEncoding[4] && newEncoding[3] == newEncoding[5])))
			return new Node("l," + node.sequence, newEncoding);

		}
		return null;
	}

	public static Node right(Node node) {
		byte[] encoding = node.grid;
		byte[] newEncoding = new byte[encoding.length];
		if (encoding[3] + 1 < encoding[1]) {
			newEncoding[2] = encoding[2];
			newEncoding[3] = (byte) (encoding[3] + 1);
			for (int i = 0; i + 1 < encoding.length; i += 2) {
				if (i != 2) {
					newEncoding[i] = encoding[i];
					newEncoding[i + 1] = encoding[i + 1];
				}
			}

			if (newEncoding.length == 6
					|| (newEncoding.length >= 8 && !(newEncoding[2] == newEncoding[4] && newEncoding[3] == newEncoding[5])))
			return new Node("r," + node.sequence, newEncoding);

		}
		return null;
	}

	public static String solverHelper(byte[] encoding, HashSet<String> stateSpace) {
		LinkedList<Node> nodes = new LinkedList<Node>();
		nodes.add(new Node("", encoding));
		while (!nodes.isEmpty()) {
			Node node = nodes.removeFirst();
			stateSpace.add(Arrays.toString(node.grid));
			if (node.grid[2] == node.grid[4] && node.grid[3] == node.grid[5] && node.grid.length == 6) {
				return node.sequence;
			}
			Node collect = collect(node);
			Node up = up(node);
			Node right = right(node);
			Node down = down(node);
			Node left = left(node);
			if (collect != null && !stateSpace.contains(Arrays.toString(collect.grid))) {
				nodes.add(collect);
			}
			if (up != null && !stateSpace.contains(Arrays.toString(up.grid))) {
				nodes.add(up);
			}
			if (right != null && !stateSpace.contains(Arrays.toString(right.grid))) {
				nodes.add(right);
			}
			if (down != null && !stateSpace.contains(Arrays.toString(down.grid))) {
				nodes.add(down);
			}
			if (left != null && !stateSpace.contains(Arrays.toString(left.grid))) {
				nodes.add(left);
			}
		}
		return null;
	}

	public static void solver(String grid) {
		String[] parsedString = grid.split(";");
		String[] sizeString = parsedString[0].split(",");
		String[] characterString = parsedString[1].split(",");
		String[] villainString = parsedString[2].split(",");
		String[] collectableString = parsedString[3].split(",");
		int totalLength = 7 * 2;
		byte[] encoding = new byte[totalLength];
		encoding[0] = Byte.parseByte(sizeString[0].trim(), 10);// m
		encoding[1] = Byte.parseByte(sizeString[1], 10);// n
		encoding[2] = Byte.parseByte(characterString[0], 10);// ix
		encoding[3] = Byte.parseByte(characterString[1], 10);// iy
		encoding[4] = Byte.parseByte(villainString[0], 10);// tx
		encoding[5] = Byte.parseByte(villainString[1], 10);// ty
		for (int i = 0; i < collectableString.length - 1; i += 2) {
			encoding[i + 6] = Byte.parseByte(collectableString[i], 10);// sx
			encoding[i + 7] = Byte.parseByte(collectableString[i + 1], 10);// sy
		}
		HashSet<String> stateSpace = new HashSet<String>();
		String solution = solverHelper(encoding, stateSpace);
//		System.out.println(solution);
		System.out.println(outputParser("s," + solution));
	}

	public static String outputParser(String solution) {
		String[] parsedString = solution.split(";");
		String[] moves = parsedString[0].split(",");
		int i = 0;
		String output = "";
		for (i = 0; i < moves.length; i++) {
			if (moves[i].equals("u")) {
				output += "result(up,";
			}
			if (moves[i].equals("d")) {
				output += "result(down,";
			}
			if (moves[i].equals("r")) {
				output += "result(right,";
			}
			if (moves[i].equals("l")) {
				output += "result(left,";
			}
			if (moves[i].equals("c")) {
				output += "result(collect,";
			}
			if (moves[i].equals("s")) {
				output += "result(snap,";
			}
			if (i == moves.length - 1) {
				output += "s0";
			}
		}
		for (; i > 0; i--) {
			output += ")";
		}
		return output;
	}

	public static String genGrid(int m, int n) {
		String grid = m + "," + n + ";";
		ArrayList<Integer> numbersSoFarX = new ArrayList<Integer>();
		ArrayList<Integer> numbersSoFarY = new ArrayList<Integer>();
		int ix = (int) (Math.random() * m);
		int iy = (int) (Math.random() * n);
		numbersSoFarX.add(ix);
		numbersSoFarY.add(iy);
		grid += ix + "," + iy + ";";
		int tx = (int) (Math.random() * m);
		int ty = (int) (Math.random() * n);
		while (numbersSoFarX.contains(tx) && numbersSoFarY.contains(ty)) {
			tx = (int) (Math.random() * m);
			ty = (int) (Math.random() * n);
		}
		numbersSoFarX.add(tx);
		numbersSoFarY.add(ty);
		grid += tx + "," + ty + ";";
		for (int i = 0; i < 4; i++) {
			int sx = (int) (Math.random() * m);
			int sy = (int) (Math.random() * n);
			while (numbersSoFarX.contains(sx) && numbersSoFarY.contains(sy)) {
				sx = (int) (Math.random() * m);
				sy = (int) (Math.random() * n);
			}
			numbersSoFarX.add(sx);
			numbersSoFarY.add(sy);
			if (i != 3) {
				grid += sx + "," + sy + ",";
			} else {
				grid += sx + "," + sy;
			}
		}

		return grid;
	}

	public static void main(String[] args) {
		String grid = genGrid(5, 5);
		printGrid(grid);
		System.out.println(grid);
		solver(grid);
		System.out.println("done");
	}

}
