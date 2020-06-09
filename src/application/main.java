package application;

import java.awt.Button;
import java.awt.TextField;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {

		Scanner sc = new Scanner(System.in);
		Group root = new Group();
		Graph g = new Graph(sc);
		g.get(sc);

		if (g.check()) {
			g.drawGraph(root);
			System.out.println("\nChoose which algorithm you wanna use:" + "\n1.BFS" + "\n2.DFS" + "\n");
			System.out.print("Enter:");
			int k = sc.nextInt();
			
			if (k < 3 && k > 0) {
				switch (k) {
				case 1:
					g.drawBFS(root);
					break;
				case 2:
					g.drawDFS(root);
					break;
				default:
					System.out.println("Wrong");
					System.exit(0);
					break;
				}
				System.out.println(g.toString(k));

				Scene scene = new Scene(root, 1000, 600);
				primaryStage.setTitle("Simulator DFS and BFS");
				primaryStage.setScene(scene);
				primaryStage.setResizable(false);
				primaryStage.show();
				
			} else {
				System.out.println("False! Please reset and run from beginning");
			}
		} else {
			System.out.println("Please reset,input matrix false");
		}

	}

	public static void main(String[] args) {
		launch(args);
	}
}
