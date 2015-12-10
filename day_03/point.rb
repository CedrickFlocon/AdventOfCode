class Point

  def initialize(x, y)
    @x=x
    @y=y
  end

  attr_accessor :x, :y

  def to_s
    x.to_s + ',' + y.to_s
  end

  def ==(p)
    @x==p.x and @y==p.y
  end

end