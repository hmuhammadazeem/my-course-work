import random
import math
import matplotlib.pyplot as plt
import numpy as np


file = open('iris_data.txt')

data = [[float(x) for x in line.split()] for line in file.readlines()]
points = np.array(data)

plt.scatter(points[:, 0], points[:, 1])
plt.show()


class KMeans:    
    def __init__(self, k=3, max_iter=300):        
        self.k = k        
        self.max_iter = max_iter        
        self.centroids = {}    
        
    def distance(self, a, b):        
        return math.sqrt(abs((a[0] - b[0]) + (a[1] - b[1]) + (a[2] - b[2]) + (a[3] - b[3])))    
    
    def fit(self, data):        
        clusters = None        
        
        for i in range(self.k):            
            self.centroids[str(i)] = data[random.randint(0, len(data) - 1)]        
            
        for i in range(self.max_iter):            
            clusters = {}            
            
            for c in range(self.k):                
                clusters[str(c)] = list()            
                
            for point in data:                
                distances = [self.distance(point, self.centroids[key]) for key in self.centroids.keys()]                
                clusters[str(distances.index(min(distances)))].append(point)            
                self.centroids = {}            
                    
            for key in clusters.keys():                
                array = np.array(clusters[key])                
                self.centroids[key] = [np.average(array[:, i]) for i in range(4)]        
            
            colors = ['g', 'r', 'b']        
            u = 0        
            for key in clusters.keys():            
                array = np.array(clusters[key])            
                plt.scatter(array[:, 1], array[:, 2], color=colors[u], s=150, linewidths=5)            
                plt.scatter(x=self.centroids[key][1], y=self.centroids[key][2], color='y')            
                u += 1        
            plt.show()        
            return clusters

KMeans().fit(data)

