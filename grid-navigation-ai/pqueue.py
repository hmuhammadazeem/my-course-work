
import heapq


class PQueue:

    def __init__(self):
        self._queue = list()
        self._index = 0

    def insert(self, item, priority):
        heapq.heappush(self._queue, (priority, self._index, item))
        self._index += 1

    def remove(self):
        return heapq.heappop(self._queue)[-1][0]

    def is_empty(self):
        return len(self._queue) == 0