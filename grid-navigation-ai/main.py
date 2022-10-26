import copy
import numpy as np
import matplotlib.pyplot as plt
from pqueue import PQueue as heap

"""

 Grid Navigation using Uninformed Searches

"""


class Point:

    def __init__(self, _x, _y):
        self.x = _x
        self.y = _y
        self.cost = 0
        self.level = None
        self.node_cost = 0

    @staticmethod
    def get_instance(point):
        return Point(int(point[0]), int(point[1]))

    def compare(self, point):
        if self.x == point.x and self.y == point.y:
            return True
        else:
            return False


f = open('grid.txt')
MOVES = list()

y, x = f.readline().split()
x, y = int(x) - 1, int(y) - 1

_start = Point.get_instance(f.readline().split())
_start.x, _start.y = _start.y, _start.x
_goal = Point.get_instance(f.readline().split())
_goal.x, _goal.y = _goal.y, _goal.x


class Node:
    def __init__(self, val, visited):
        self.val = val
        self.visited = visited


class Navigate:

    def get_cost(self, move: str):
        if move == "up":
            return 1
        elif move == "right":
            return 3
        else:
            return 2

    def get_key(self, cost):
        if cost == 1:
            return "up"
        elif cost == 3:
            return "right"
        else:
            return "upright"

    def move_up(self, coordinates: Point):
        p = Point(coordinates.x, coordinates.y)
        if not p.x == x:
            p.x = p.x + 1
            return p
        return None

    def move_right(self, coordinates: Point):
        p = Point(coordinates.x, coordinates.y)
        if not p.y == y:
            p.y = p.y + 1
            return p
        return None

    def move_upright(self, coordinates: Point):
        p = Point(coordinates.x, coordinates.y)
        if not p.y == y and not p.x == x:
            p.x = p.x + 1
            p.y = p.y + 1
            return p
        return None

    def move_up_in(self, coordinates: Point):
        p = Point(coordinates.x, coordinates.y)
        if not p.x == 0:
            p.x = p.x - 1
            return p
        return None

    def move_right_in(self, coordinates: Point):
        p = Point(coordinates.x, coordinates.y)
        if not p.y == 0:
            p.y = p.y - 1
            return p
        return None

    def move_upright_in(self, coordinates: Point):
        p = Point(coordinates.x, coordinates.y)
        if not p.y == 0 and not p.x == 0:
            p.x = p.x - 1
            p.y = p.y - 1
            return p
        return None

    def get_min_cost(self, keys):
        costs = [self.get_cost(key) for key in keys]
        return min(costs)

    def hash_key(self, point):
        return str(point.x) + ":" + str(point.y)

    def pop_dict(self, dict):
        keys = list(dict.keys())
        element = copy.deepcopy(dict[keys[0]])
        del dict[keys[0]]
        return element

    def BFS(self, graph: list, start, goal):

        queue = list()
        PATH = list()
        COST = 0

        queue.append(Point(start.x, start.y))
        graph[start.x][start.y].visited = True
        PATH.append((start.x, start.y))

        if queue[0].compare(goal):
            return PATH, COST

        while queue:

            current_node = queue.pop(0)

            moves = {"up": self.move_up(current_node),
                     "right": self.move_right(current_node),
                     "upright": self.move_upright(current_node)}

            for key in moves.keys():
                move = moves[key]
                if move is None:
                    continue
                if not graph[move.x][move.y].visited and not graph[move.x][move.y].val:
                    graph[move.x][move.y].visited = True
                    queue.append(copy.deepcopy(move))
                    PATH.append((move.x, move.y))
                    COST += self.get_cost(key)
                    MOVES.append(key)
                    if move.compare(goal):
                        graph[move.x][move.y].visited = 11
                        break
        plt.imshow(np.array([[u.visited for u in t] for t in graph]))
        plt.show()
        return PATH, COST

    def DFS(self, graph: list, start, goal):

        queue = list()
        COST = 0
        PATH = list()

        queue.append(Point(start.x, start.y))
        graph[start.x][start.y].visited = True
        PATH.append((start.x, start.y))

        while queue:

            current_node = queue.pop(-1)

            if not graph[current_node.x][current_node.y].visited:
                graph[current_node.x][current_node.y].visited = True
                PATH.append((current_node.x, current_node.y))
                COST += current_node.cost

            if current_node.compare(goal):
                graph[current_node.x][current_node.y].visited = 11
                break

            moves = {"upright": self.move_upright(current_node),
                     "right": self.move_right(current_node),
                     "up": self.move_up(current_node)}

            for key in moves.keys():
                move = moves[key]
                if move is None:
                    continue
                if not graph[move.x][move.y].visited and not graph[move.x][move.y].val:
                    move.cost = self.get_cost(key)
                    queue.append(copy.deepcopy(move))
        plt.imshow(np.array([[u.visited for u in t] for t in graph]))
        plt.show()
        return PATH, COST

    def DLS(self, graph: list, start, MAX_LEVEL, goal):

        queue = list()
        COST = 0
        current_node = None
        goal_found = False
        PATH = []

        queue.append(Point(start.x, start.y))
        queue[0].level = 0


        while queue:

            current_node = queue.pop(-1)

            if not graph[current_node.x][current_node.y].visited:
                graph[current_node.x][current_node.y].visited = True
                PATH.append((current_node.x, current_node.y))
                COST += current_node.cost

            if current_node.compare(goal):
                graph[current_node.x][current_node.y].visited = 11
                goal_found = True
                break

            if current_node.level < MAX_LEVEL:
                moves = {"upright": self.move_upright(current_node),
                         "right": self.move_right(current_node),
                         "up": self.move_up(current_node)}
            else:
                moves = {}

            for key in moves.keys():
                move = moves[key]
                if move is None:
                    continue
                if not graph[move.x][move.y].visited and not graph[move.x][move.y].val:
                    move.level = current_node.level + 1
                    move.cost = self.get_cost(key)
                    queue.append(copy.deepcopy(move))
        return PATH, COST, plt, current_node, graph, goal_found

    def IDS(self, graph: list, start, goal):
        depth = 0
        while depth < 100:
            PATH, COST, plt, current_node, _graph, goal_found = self.DLS(copy.deepcopy(graph), start, depth, goal)
            if goal_found:
                break
            depth += 1
            PATH, COST = None, None
        plt.title("Iterative Deepening Search")
        plt.imshow(np.array([[u.visited for u in t] for t in _graph]))
        plt.show()
        return PATH, COST

    def uniform_cost(self, graph: list, start, goal):

        queue = heap()
        PATH = []
        COST = 0

        queue.insert((start, 0), 0)
        graph[start.x][start.y].visited = True

        while not queue.is_empty():

            current_node = queue.remove()
            PATH.append((current_node.x, current_node.y))
            COST += current_node.node_cost

            if current_node.compare(goal):
                graph[current_node.x][current_node.y].visited = 11
                break

            moves = {"up": self.move_up(current_node),
                     "right": self.move_right(current_node),
                     "upright": self.move_upright(current_node)}

            for key in moves.keys():
                move = moves[key]
                if move is None:
                    continue
                if not graph[move.x][move.y].visited and not graph[move.x][move.y].val:
                    graph[move.x][move.y].visited = True
                    move.cost = current_node.cost + self.get_cost(key)
                    move.node_cost = self.get_cost(key)
                    queue.insert((copy.deepcopy(move), move.cost), move.cost)
        plt.imshow(np.array([[u.visited for u in t] for t in graph]))
        plt.show()
        return PATH, COST

    def bidir_search(self, graph, start, goal):
        queue1 = {}
        queue2 = {}
        PATH1 = list()
        PATH2 = list()
        COST = 0
        graph1 = copy.deepcopy(graph)

        queue1[self.hash_key(start)] = copy.deepcopy(start)
        queue2[self.hash_key(goal)] = copy.deepcopy(goal)
        graph[start.x][start.y].visited = 8
        graph1[goal.x][goal.y].visited = 7
        graph2 = copy.deepcopy(graph)

        graph2[start.x][start.y].visited = 8
        graph2[goal.x][goal.y].visited = 7

        PATH1.append((start.x, start.y))
        PATH2.append((goal.x, goal.y))

        current1 = None
        current2 = None

        while queue1 and queue2:
            if queue1:
                current1 = self.pop_dict(queue1)
                if current1.compare(goal) or self.hash_key(current1) in queue2.keys():
                    graph2[current1.x][current1.y].visited = 45
                    break

                moves = {"up": self.move_up(current1),
                         "right": self.move_right(current1),
                         "upright": self.move_upright(current1)}

                for key in moves.keys():
                    move = moves[key]
                    if move is None:
                        continue
                    if not graph[move.x][move.y].visited and not graph[move.x][move.y].val:
                        graph[move.x][move.y].visited = 65
                        graph2[move.x][move.y].visited = 65
                        queue1[self.hash_key(move)] = copy.deepcopy(move)
                        PATH1.append((move.x, move.y))
                        COST += self.get_cost(key)

                if queue2:
                    current2 = self.pop_dict(queue2)
                    if current2.compare(start) or self.hash_key(current2) in queue1:
                        graph2[current2.x][current2.y].visited = 45
                        break

                    _moves = {"up": self.move_up_in(current2),
                             "right": self.move_right_in(current2),
                             "upright": self.move_upright_in(current2)}

                    for key in _moves.keys():
                        _move = _moves[key]
                        if _move is None:
                            continue
                        if not graph1[_move.x][_move.y].visited and not graph1[_move.x][_move.y].val:
                            graph1[_move.x][_move.y].visited = 34
                            if graph2[_move.x][_move.y].visited != 65:
                                graph2[_move.x][_move.y].visited = 34
                            queue2[self.hash_key(_move)] = copy.deepcopy(_move)
                            PATH2.append((_move.x, _move.y))
                            COST += self.get_cost(key)
        plt.imshow(np.array([[u.visited for u in t] for t in graph2]))
        plt.show()
        return (PATH1, PATH2), COST

    #
    # Informed Searches: Heuristic Functions
    #

    # Diagonal distance heuristic
    def best_first_heuristic(self, n, goal):
        h_diagonal = min(abs(n.x - goal.x), abs(n.y - goal.y))
        h_straight = (abs(n.x - goal.x) + abs(n.y - goal.y))
        heuristic_func = 2 * h_diagonal + 1 * (h_straight - 2 * h_diagonal) + 3 * (h_straight - 2 * h_diagonal)
        return heuristic_func

    def best_first(self, graph, start, goal):
        queue = heap()
        PATH = []
        COST = 0

        queue.insert((start, 0), 0)
        graph[start.x][start.y].visited = True

        while not queue.is_empty():

            current_node = queue.remove()
            PATH.append((current_node.x, current_node.y))
            COST += current_node.node_cost

            if current_node.compare(goal):
                graph[current_node.x][current_node.y].visited = 11
                break

            moves = {"up": self.move_up(current_node),
                     "right": self.move_right(current_node),
                     "upright": self.move_upright(current_node)}

            for key in moves.keys():
                move = moves[key]
                if move is None:
                    continue
                if not graph[move.x][move.y].visited and not graph[move.x][move.y].val:
                    graph[move.x][move.y].visited = True
                    move.node_cost = self.get_cost(key)
                    move.cost = (current_node.cost + move.node_cost) + self.best_first_heuristic(move, goal)
                    queue.insert((copy.deepcopy(move), move.cost), move.cost)
        plt.imshow(np.array([[u.visited for u in t] for t in graph]))
        plt.show()
        return PATH, COST

    def a_star(self, graph, start, goal):
        queue = heap()
        PATH = []
        COST = 0

        queue.insert((start, 0), 0)
        graph[start.x][start.y].visited = True

        while not queue.is_empty():

            current_node = queue.remove()
            PATH.append((current_node.x, current_node.y))
            COST += current_node.node_cost

            if current_node.compare(goal):
                graph[current_node.x][current_node.y].visited = 11
                break

            moves = {"up": self.move_up(current_node),
                     "right": self.move_right(current_node),
                     "upright": self.move_upright(current_node)}

            for key in moves.keys():
                move = moves[key]
                if move is None:
                    continue
                if not graph[move.x][move.y].visited and not graph[move.x][move.y].val:
                    graph[move.x][move.y].visited = True
                    move.cost = self.best_first_heuristic(move, goal)
                    move.node_cost = self.get_cost(key)
                    queue.insert((copy.deepcopy(move), move.cost), move.cost)
        plt.imshow(np.array([[u.visited for u in t] for t in graph]))
        plt.show()
        return PATH, COST

    def ida_util(self, graph: list, start, MAX_LEVEL, goal):

        queue = list()
        COST = 0
        current_node = None
        goal_found = False
        PATH = []

        queue.append(Point(start.x, start.y))
        queue[0].level = 0
        f_cost = 0
        # graph[start.x][start.y].visited = True
        # PATH.append((start.x, start.y))

        while queue:

            current_node = queue.pop(-1)

            if self.best_first_heuristic(current_node, goal) > MAX_LEVEL:
                f_cost = self.best_first_heuristic(current_node, goal)
                break

            if not graph[current_node.x][current_node.y].visited:
                graph[current_node.x][current_node.y].visited = True
                PATH.append((current_node.x, current_node.y))
                COST += current_node.cost

            if current_node.compare(goal):
                graph[current_node.x][current_node.y].visited = 11
                goal_found = True
                break

            moves = {"upright": self.move_upright(current_node),
                     "right": self.move_right(current_node),
                     "up": self.move_up(current_node)}

            for key in moves.keys():
                move = moves[key]
                if move is None:
                    continue
                if not graph[move.x][move.y].visited and not graph[move.x][move.y].val:
                    move.level = current_node.level + 1
                    move.cost = self.get_cost(key)
                    queue.append(copy.deepcopy(move))
        return PATH, COST, plt, current_node, graph, goal_found, f_cost

    def ida_star(self, graph: list, start, goal):
        depth = 0
        while depth < 100:
            PATH, COST, plt, current_node, _graph, goal_found, f_cost = self.ida_util(copy.deepcopy(graph), start, depth, goal)
            if goal_found:
                break
            depth = f_cost
            PATH, COST = None, None
        plt.title("IDA*")
        plt.imshow(np.array([[u.visited for u in t] for t in _graph]))
        plt.show()
        return PATH, COST


p = [[Node(int(node), False) for node in line.split()] for line in f.readlines()]
_graph = list()

for i in range(len(p)):
    _graph.append(p.pop(-1))

if __name__ == '__main__':
    print('1. BFS\n2. DFS\n3. IDS\n4. DLS\n5. Uniform Cost\n6. Bidirectional\n7. Best First Search\n8. A Star\n9. IDA Star')
    n = int(input("Select a search strategy: "))

    if n is 1:
        path, cost = Navigate().BFS(_graph, _start, _goal)
        print(path)
        print("Cost: " + str(cost))
    elif n is 2:
        path, cost = Navigate().DFS(_graph, _start, _goal)
        print(path)
        print("Cost: " + str(cost))
    elif n is 3:
        path, cost = Navigate().IDS(_graph, _start, _goal)
        print(path)
        print("Cost: " + str(cost))
    elif n is 4:
        limit = int(input("Enter a depth limit: "))
        path, cost, plt, current_node, graph, goal_found = Navigate().DLS(_graph, _start, limit, _goal)
        plt.title("Depth Limited Search")
        plt.imshow(np.array([[u.visited for u in t] for t in graph]))
        plt.show()
    elif n is 5:
        path, cost = Navigate().uniform_cost(_graph, _start, _goal)
        print(path)
        print("Cost: " + str(cost))
    elif n is 6:
        path, cost = Navigate().bidir_search(_graph, _start, _goal)
        print(path[0])
        print(path[1])
        print("Cost: " + str(cost))
    elif n is 7:
        path, cost = Navigate().best_first(_graph, _start, _goal)
        print(path)
        print("Cost: " + str(cost))
    elif n is 8:
        path, cost = Navigate().a_star(_graph, _start, _goal)
        print(path)
        print("Cost: " + str(cost))
    elif n is 9:
        path, cost = Navigate().ida_star(_graph, _start, _goal)
        print(path)
        print("Cost: " + str(cost))
    else:
        print('Wrong selection!')

