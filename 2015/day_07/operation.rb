class Operation

  def initialize(action, values)
    @values = values
    @action = action
  end

  def compute
    case @action
      when 'LSHIFT'
        @values[0] << @values[1]
      when 'RSHIFT'
        @values[0] >> @values[1]
      when 'AND'
        @values[0] & @values[1]
      when 'OR'
        @values[0] | @values[1]
      when 'NOT'
        ~@values[0]
      else
        @values[0]
    end
  end
end