from flask import Flask, render_template, request, jsonify
from app.model import Model
import json


app = Flask(__name__)


@app.route('/')
def index():
    return render_template('index.html')


@app.route('/classify', methods=['GET', 'POST'])
def classify():
    
    if request.method == 'POST':
        
        data = json.loads(request.data)

        sepal_l = data.get('sepal_l')
        sepal_w = data.get('sepal_w')
        petal_l = data.get('petal_l')
        petal_w = data.get('petal_w')

        prediction_model = Model(sepal_l, sepal_w, petal_l, petal_w)

        predict_class = prediction_model.predict_class()

        ret_data = {'iris_class': predict_class}

        return jsonify(ret_data)
    return 'No input'