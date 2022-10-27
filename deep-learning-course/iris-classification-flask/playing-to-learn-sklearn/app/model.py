import sklearn
import numpy as np
import pandas as pd
from sklearn.tree import DecisionTreeClassifier

class Model:

    def __init__(self, sepal_l, sepal_w, petal_l, petal_w):
        self.data = pd.read_csv("app/iris.csv")
        self.sepal_l = sepal_l
        self.sepal_w = sepal_w
        self.petal_l = petal_l
        self.petal_w = petal_w
        self.tree_clf = DecisionTreeClassifier()
        
    def split_train_test(self, data, test_ratio):
        shuffled_indices = np.random.permutation(len(data))
        test_set_size = int(len(data) * test_ratio)
        test_indices = shuffled_indices[:test_set_size]
        train_indices = shuffled_indices[test_set_size:]
        return data.iloc[train_indices], data.iloc[test_indices]

    def train(self):
        train, test = self.split_train_test(self.data, 0.2)
        X = train.iloc[:, 0:4]
        y = train.iloc[:, 4]
        test = None
        self.tree_clf.fit(X, y)
        
    def predict_class(self):
        self.train()
        predcit_for = [self.sepal_l, self.sepal_w, self.petal_l, self.petal_w]
        prediction = self.tree_clf.predict([predcit_for])[0]
        return prediction

        