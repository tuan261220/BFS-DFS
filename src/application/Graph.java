package application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Group;
import java.util.Queue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.Stack;

public class Graph {
	private int size;
	private int graph[][];
	private Circle node[];
	private ArrayList<Line> edge;
	private ArrayList<Integer> bfs;
	private ArrayList<Integer> dfs;

	public Graph(Scanner sc) {
		System.out.print("Enter the size:");

		// number of vertices
		size = sc.nextInt();

		// the data of the graph with 0,1 or the weight
		graph = new int[size][size];

		// showing the vertices with circles and saving them in an array
		node = new Circle[size];

		// showing and saving the edges between vertices in an arraylist
		edge = new ArrayList<Line>();

		// saving the vertices by bfs algorithm
		bfs = new ArrayList<Integer>(size);

		// saving the vertices by dfs alglorithm
		dfs = new ArrayList<Integer>(size);
	}

	public Graph(int size) {
		this.size = size;
		graph = new int[size][size];
		node = new Circle[size];
		edge = new ArrayList<Line>();
		bfs = new ArrayList<>(size);
		dfs = new ArrayList<>(size);
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getGraph(int i, int j) {
		return graph[i][j];
	}

	public void setGraph(int i, int j, int value) {
		graph[i][j] = value;
	}

	public String toString(int k) {
		if (k == 1) {
			return "BFS:" + bfs.toString();
		} else {
			return "DFS" + dfs.toString();
		}
	}

	public void drawGraph(Group root) {
		// set local random node
		for (int i = 0; i < node.length; i++) {
			node[i] = new Circle(15, Color.LIGHTGRAY);
			node[i].setEffect(new DropShadow(1, 3, 3, Color.GRAY));

			node[i].setCenterX((Math.random()) * 700 + 200);
			node[i].setCenterY((Math.random()) * 350 + 200);

			root.getChildren().add(node[i]);
		}

		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph[0].length; j++) {

				// draw line between two points
				if (graph[i][j] > 0) {
					edge.add(new Line(node[i].getCenterX(), node[i].getCenterY(), node[j].getCenterX(),
							node[j].getCenterY()));

				}

				// add text in circle
				Text t = new Text(node[i].getCenterX() - 4, node[i].getCenterY() + 4, "" + (i + 1));
				t.setFill(Color.BLUE);
				t.setStroke(Color.BLACK);
				root.getChildren().add(t);
			}
		}
		root.getChildren().addAll(edge);
		for (int i = 0; i < edge.size(); i++) {
			edge.get(i).toBack();
		}
	}

	public void get(Scanner sc) {
		System.out.println("Input matrix:");
		try {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					graph[i][j] = sc.nextInt();
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Array index bound Exception");
		}
		if (check()) {
			System.out.print("Enter the start point:");
			int start = sc.nextInt();
			bfs(start - 1);
			dfs(start - 1);
		} else {
			System.out.println("please Input matrix again");
		}
	}

	private void dfs(int start) {
		boolean flag = false;

		// Create stack for DFS
		Stack<Integer> stack = new Stack<Integer>();

		// Mark all the vertices as not visited(By default
		// set as false)

		ArrayList<Boolean> visited = new ArrayList<Boolean>();
		for (int i = 0; i < size; i++) {
			visited.add(false);
		}

		// Mark the current node as visited
		stack.add(start);
		visited.add(start, true);

		// add a vertex to dfs list
		dfs.add(start + 1);

		while (stack.size() != 0) {
			int current = stack.peek();
			for (int i = 0; i < size; i++) {
				if (graph[current][i] != 0 && !visited.get(i)) {
					stack.add(i);
					dfs.add(i + 1);
					visited.set(i, true);
					flag = true;
					break;
				}
			}
			if (!flag) {
				stack.pop();
			}
			flag = false;
		}
	}

	private void bfs(int start) {
		// Create queue for BFS
		Queue<Integer> queue = new LinkedList<Integer>();

		// Mark all the vertices as not visited(By default
		// set as false)
		ArrayList<Boolean> visited = new ArrayList<Boolean>(size);
		for (int i = 0; i < size; i++) {
			visited.add(false);
		}

		// Mark the current node as visited and enqueue it
		queue.add(start);
		visited.set(start, true);

		while (queue.size() != 0) {
			// Dequeue a vertex from queue
			int current = queue.element();
			queue.remove();

			// add a vertex to bfs list
			bfs.add(current + 1);

			// Get all adjacent vertices of the dequeued vertex current
			// If a adjacent has not been visited, then mark it
			// visited and enqueue it
			for (int i = 0; i < size; i++) {
				if (graph[current][i] != 0 && !visited.get(i)) {
					queue.add(i);
					visited.set(i, true);
				}
			}
		}
	}

	public boolean check() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (graph[i][j] < 0 || graph[i][j] > 1) {
					return false;
				}
			}
		}
		return true;
	}

	public void drawBFS(Group root) {
		// loading background image button
		Image start = new Image("file:///C:/Users/DELL/eclipse-workspace/DSAA project/src/application/start.jpg");
		ImageView bgstart = new ImageView(start);
		bgstart.setFitHeight(30);
		bgstart.setFitWidth(30);

		Image next = new Image("file:///C:/Users/DELL/eclipse-workspace/DSAA project/src/application/next.jpg");
		ImageView bgnext = new ImageView(next);
		bgnext.setFitHeight(30);
		bgnext.setFitWidth(30);

		Image finish = new Image("file:///C:/Users/DELL/eclipse-workspace/DSAA project/src/application/finish.jpg");
		ImageView bgfinish = new ImageView(finish);
		bgfinish.setFitHeight(30);
		bgfinish.setFitWidth(30);

		// Create a button
		Button btn = new Button("", bgstart);

		Circle c = new Circle(15, null);
		c.setVisible(false);
		c.setStrokeWidth(3);
		c.setStroke(Color.BLACK);

		Text txtresult = new Text(100, 30, "RESULT: ");
		txtresult.setStrokeWidth(10);
		txtresult.setFill(Color.BLACK);

		root.getChildren().add(btn);
		root.getChildren().add(c);
		root.getChildren().add(txtresult);

		btn.setOnAction(new EventHandler<ActionEvent>() {
			int count = 0;

			@Override
			public void handle(ActionEvent event) {
				c.setVisible(true);

				btn.setGraphic(bgnext);

				if (count == size) {
					c.setVisible(false);
					System.out.println();
					System.exit(0);
				} else {

					c.setCenterX(node[bfs.get(count) - 1].getCenterX());
					c.setCenterY(node[bfs.get(count) - 1].getCenterY());

					node[bfs.get(count) - 1].setFill(Color.PINK);

					root.getChildren().add(new Text(160 + 15 * count, 30, "" + bfs.get(count)));
					count++;

					if (count == size) {
						btn.setGraphic(bgfinish);
					}
				}
			}
		});
	}

	public void drawDFS(Group root) {
		// loading background image button
		Image start = new Image("file:///C:/Users/DELL/eclipse-workspace/DSAA project/src/application/start.jpg");
		ImageView bgstart = new ImageView(start);
		bgstart.setFitHeight(30);
		bgstart.setFitWidth(30);

		Image next = new Image("file:///C:/Users/DELL/eclipse-workspace/DSAA project/src/application/next.jpg");
		ImageView bgnext = new ImageView(next);
		bgnext.setFitHeight(30);
		bgnext.setFitWidth(30);

		Image finish = new Image("file:///C:/Users/DELL/eclipse-workspace/DSAA project/src/application/finish.jpg");
		ImageView bgfinish = new ImageView(finish);
		bgfinish.setFitHeight(30);
		bgfinish.setFitWidth(30);

		// Create a button
		Button btn = new Button("", bgstart);

		Circle c = new Circle(15, null);
		c.setVisible(false);
		c.setStrokeWidth(3);
		c.setStroke(Color.BLACK);

		Text txtresult = new Text(100, 30, "RESULT: ");
		txtresult.setStrokeWidth(10);
		txtresult.setFill(Color.BLACK);

		root.getChildren().add(btn);
		root.getChildren().add(c);
		root.getChildren().add(txtresult);

		btn.setOnAction(new EventHandler<ActionEvent>() {
			int count = 0;

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				c.setVisible(true);

				btn.setGraphic(bgnext);

				if (count == size) {
					c.setVisible(false);
					System.out.println();
					System.exit(0);
				} else {

					c.setCenterX(node[dfs.get(count) - 1].getCenterX());
					c.setCenterY(node[dfs.get(count) - 1].getCenterY());

					node[dfs.get(count) - 1].setFill(Color.PINK);

					root.getChildren().add(new Text(160 + 15 * count, 30, "" + dfs.get(count)));
					count++;

					if (count == size) {
						btn.setGraphic(bgfinish);
					}
				}
			}
		});
	}
}
